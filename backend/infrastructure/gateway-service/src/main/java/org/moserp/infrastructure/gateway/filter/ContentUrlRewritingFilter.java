package org.moserp.infrastructure.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.CharStreams;
import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedHashMap;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Component
public final class ContentUrlRewritingFilter extends ZuulFilter {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final ImmutableSet<MediaType> DEFAULT_SUPPORTED_TYPES = ImmutableSet.of(MediaType.APPLICATION_JSON);

    @Autowired
    private ResponseLinksMapper responseLinksMapper;

    private final ImmutableSet<MediaType> supportedTypes;

    public ContentUrlRewritingFilter() {
        this.supportedTypes = ImmutableSet.copyOf(checkNotNull(DEFAULT_SUPPORTED_TYPES));
    }

    private static boolean containsContent(final RequestContext context) {
        assert context != null;
        return context.getResponseDataStream() != null || context.getResponseBody() != null;
    }

    private static boolean supportsType(final RequestContext context, final Collection<MediaType> supportedTypes) {
        assert supportedTypes != null;
        for (MediaType supportedType : supportedTypes) {
            MediaType responseMediaType = getResponseMediaType(context);
            log.debug("Response Media Type: " + responseMediaType);
            if (supportedType.isCompatibleWith(responseMediaType)) return true;
        }
        return false;
    }

    private static MediaType getResponseMediaType(final RequestContext context) {
        assert context != null;
        for (final Pair<String, String> header : context.getZuulResponseHeaders()) {
            if (header.first().equalsIgnoreCase(CONTENT_TYPE)) {
                return MediaType.parseMediaType(header.second());
            }
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        final RequestContext context = RequestContext.getCurrentContext();
        return hasSupportedBody(context);
    }

    public boolean hasSupportedBody(RequestContext context) {
        return containsContent(context) && supportsType(context, this.supportedTypes);
    }

    @Override
    public Object run() {
        try {
            rewriteContent(RequestContext.getCurrentContext());
        } catch (final Exception e) {
            Throwables.propagate(e);
        }
        return null;
    }

    private void rewriteContent(final RequestContext context) throws Exception {
        assert context != null;
        String responseBody = getResponseBody(context);
        if (responseBody != null) {
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> map = mapper.readValue(responseBody, LinkedHashMap.class);
            responseLinksMapper.fixFor(map);
            String body = mapper.writeValueAsString(map);
            context.setResponseBody(body);
        }
    }

    private String getResponseBody(RequestContext context) throws IOException {
        String responseData = null;
        if (context.getResponseBody() != null) {
            context.getResponse().setCharacterEncoding("UTF-8");
            responseData = context.getResponseBody();

        } else if (context.getResponseDataStream() != null) {
            context.getResponse().setCharacterEncoding("UTF-8");
            try (final InputStream responseDataStream = context.getResponseDataStream()) {
                //FIXME What about character encoding of the stream (depends on the response content type)?
                responseData = CharStreams.toString(new InputStreamReader(responseDataStream));
            }
        }
        return responseData;
    }

}
