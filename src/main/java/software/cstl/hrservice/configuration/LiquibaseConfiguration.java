package software.cstl.hrservice.configuration;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import software.cstl.hrservice.utils.HRServiceConstants;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class LiquibaseConfiguration {
    private final Environment env;
    private final DataSource dataSource;

    public LiquibaseConfiguration(Environment env, DataSource dataSource) {
        this.env = env;
        this.dataSource = dataSource;
    }

    @Bean
    public SpringLiquibase liquibase(){
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.xml");
        liquibase.setDataSource(dataSource);
        if (env.acceptsProfiles(Profiles.of(HRServiceConstants.SPRING_PROFILE_NO_LIQUIBASE))) {
            liquibase.setShouldRun(false);
        } else {
            log.debug("Configuring Liquibase");
        }
        return liquibase;
    }
}
