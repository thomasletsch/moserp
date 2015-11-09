package org.moserp.common.structure;

public class GroupResolver {

    public static final String DOMAIN_SUB_PACKAGE = ".domain";

    public String resolveGroup(Class<?> domainType) {
        String packageName = domainType.getPackage().getName();
        if(packageName.contains(DOMAIN_SUB_PACKAGE)) {
            packageName = packageName.substring(0, packageName.lastIndexOf(DOMAIN_SUB_PACKAGE));
        }
        return packageName;
    }
}
