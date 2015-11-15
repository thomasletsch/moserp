package org.moserp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * LDAP based security:
 * application.yml:
 * <pre>
 *     ldap:
 *     contextSource:
 *     url: ldap://172.17.0.3:389/
 *     userDn: cn=admin,dc=moserp,dc=org
 *     password: Nosfols6
 * </pre>
 *
 * Configure LDAP server with:<br>
 * - BaseDN="dc=moserp,dc=org"  (LDAP_DOMAIN="moserp.org") <br>
 * - Admin user "admin" (default) and password "Nosfols6"  (=> userDn="cn=admin,dc=moserp,dc=org") <br>
 * - groups (as a base for all groups / roles): <br>
 * <pre>
 *     dn: ou=groups,dc=moserp,dc=org
 *     objectclass: top
 *     objectclass: organizationalUnit
 *     ou: groups
 * </pre>
 * - users group: <br>
 * <pre>
 *     dn: cn=users,ou=groups,dc=moserp,dc=org
 *     objectclass: top
 *     objectclass: groupOfUniqueNames
 *     cn: users
 *     uniqueMember: uid=testuser,ou=people,dc=moserp,dc=org
 * </pre>
 * - people organisational unit: <br>
 * <pre>
 *     dn: ou=people,dc=moserp,dc=org
 *     objectclass: top
 *     objectclass: organizationalUnit
 *     ou: people
 * </pre>
 * - testuser Test User: <br>
 * <pre>
 *     dn: uid=testuser,ou=people,,dc=moserp,dc=org
 *     objectclass: top
 *     objectclass: person
 *     objectclass: organizationalPerson
 *     objectclass: inetOrgPerson
 *     cn: Test User
 *     sn: Test
 *     uid: testuser
 *     userPassword: <password>
 * </pre>
 */
@Configuration
@EnableWebSecurity
public class GlobalAuthenticationConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication().contextSource(contextSource()).userDnPatterns("uid={0},ou=people,dc=moserp,dc=org").groupSearchBase("ou=groups,dc=moserp,dc=org");
    }

    @Bean
    @ConfigurationProperties(prefix="ldap.contextSource")
    public LdapContextSource contextSource() {
        return new LdapContextSource();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().hasAnyRole("USERS").and().formLogin().and().httpBasic();;
    }
}
