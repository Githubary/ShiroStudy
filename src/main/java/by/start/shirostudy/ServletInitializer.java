package by.start.shirostudy;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author bystart
 * @date 2020/7/17 9:26
 * 仔细！坚持！
 * ❥(^_-))
 */

public class ServletInitializer extends SpringBootServletInitializer {
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            return builder.sources(ShirostudyApplication.class);
        }
}
