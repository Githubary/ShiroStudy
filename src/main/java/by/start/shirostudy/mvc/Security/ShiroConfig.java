package by.start.shirostudy.mvc.Security;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import by.start.shirostudy.common.Redis.RedisUtils;
import by.start.shirostudy.common.shiro.Filter.PermsFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import by.start.shirostudy.common.shiro.Cache.RedisCacheManager;

import javax.servlet.Filter;
import java.util.*;

/**
 * @author bystart
 * @date 2020/7/4 16:03
 * 仔细！坚持！
 * ❥(^_-))
 */

@Configuration
public class ShiroConfig {

//    过滤器的设置



    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager")DefaultWebSecurityManager securityManager){
         ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
         HashMap<String,Filter> filters = new HashMap<>();
         filters.put("perms",new PermsFilter());
         bean.setFilters(filters);
         bean.setLoginUrl("/login");
         bean.setSuccessUrl("/index");
         bean.setUnauthorizedUrl("/noAuth");
         bean.setFilterChainDefinitionMap(getFilterChainDefinitionMap());
         bean.setSecurityManager(securityManager);
         return bean;
    }

    private Map<String, String> getFilterChainDefinitionMap() {
        Map<String,String> map = new LinkedHashMap<>();
        map.put("/static/**","anon");
        map.put("/css/**","anon");
        map.put("/js/**","anon");
        map.put("/img/**","anon");
        map.put("/docs/**","anon");
        map.put("/fonts/**","anon");
        map.put("/plugins/**","anon");
        map.put("/logout","logout");
        map.put("/error/**","anon");
        map.put("/login","anon");
        map.put("/index","user");
        map.put("/bystart","user");
        map.put("/**","authc");
        return map;
    }

//    会话管理的设置

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("modularRealmAuthenticator")ModularRealmAuthenticator modularRealm,
                                                                  @Qualifier("myRealm")MyRealm myRealm,
                                                                  @Qualifier("mySecondRealm")MySecondRealm mySecondRealm,
                                                                  @Qualifier("redisCacheManager")RedisCacheManager redisCacheManager,
                                                                  @Qualifier("rememberMeManager")RememberMeManager rememberMeManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        /**设置身份验证器
         * 分为单验证和多验证，这里采用的是多验证，单验证就可以不用配置了
         * setAuthenticator要在setRealms之前设置，不然会出现覆盖问题，导致realm丢失报错
         * */
        securityManager.setAuthenticator(modularRealm);
        /**
         * 多验证身份的情况下，有多个Realm，所以要加入到集合中
         */
        List<Realm> realms = new ArrayList<>();
        realms.add(myRealm);
        realms.add(mySecondRealm);
        securityManager.setRealms(realms);

        /**
         * 设置缓存
         */
        securityManager.setCacheManager(redisCacheManager);

        /**
         * 设置记住我
         */
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

//    多Realms的配置


    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        ModularRealmAuthenticator modularRealm  = new ModularRealmAuthenticator();
        /**设置策略
         * 有三种
         * new AllSuccessfulStrategy()  两个Realm都成功才能认证成功
         * new AtLeastOneSuccessfulStrategy()  至少一个成功才能认证成功
         * new FirstSuccessfulStrategy()   第一个Realm成功就认证成功
         * */
        modularRealm.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return  modularRealm;
    }



    @Bean(name = "myRealm")
    public MyRealm getMyRealm(@Qualifier("hashedCredentialsMatcher")HashedCredentialsMatcher matcher){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(matcher);
        return myRealm;
    }

    @Bean(name = "mySecondRealm")
    public MySecondRealm getMySecondRealm(@Qualifier("hashedCredentialsMatcher2")HashedCredentialsMatcher matcher){
        MySecondRealm mySecondRealm = new MySecondRealm();
        mySecondRealm.setCredentialsMatcher(matcher);
        return mySecondRealm;
    }




//    加密方式的配置
    /**
     * 非常重要的，要加入到Realm中，用于对token中的密码进行加密的东西
     */

    /**SHA-1加密功能*/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA-1");
        matcher.setHashIterations(1024);
        return matcher;
    }

    /**MD5加密功能*/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher2(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        return matcher;
    }


//  shiro-redis缓存的设置
    /**
     * shiro缓存管理器;
     * 需要添加到securityManager中
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(@Qualifier("redisManager") RedisUtils redisUtils){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisUtils(redisUtils);
        //redis中针对不同用户缓存
        redisCacheManager.setPrincipalIdFieldName("username");
        System.out.println(redisCacheManager.getPrincipalIdFieldName());
        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }

    @Bean
    public RedisUtils redisManager(){
        RedisUtils redisUtils = new RedisUtils();
        return redisUtils;
    }


//  RememberMe功能的配置

    @Bean
    public CookieRememberMeManager rememberMeManager(@Qualifier("rememberMeCookie")SimpleCookie cookie){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(cookie);
        return rememberMeManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setMaxAge(20);
        return cookie;
    }


    /**
     * shiro 的生命周期管理
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 加上了这个后，shiro和thymeleaf才能结合
     * 前端才能根据有无权限来显示对应的div
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }



}

