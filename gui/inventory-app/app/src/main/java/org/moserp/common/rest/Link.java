package org.moserp.common.rest;

public class Link {

    public static final String REL_SELF = "self";
    public static final String REL_FIRST = "first";
    public static final String REL_PREVIOUS = "prev";
    public static final String REL_NEXT = "next";
    public static final String REL_LAST = "last";

    private String href;

    public Link() {
    }

    public Link(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        return href.equals(link.href);

    }

    @Override
    public int hashCode() {
        return href.hashCode();
    }


}
