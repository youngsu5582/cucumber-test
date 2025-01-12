package config;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import java.util.HashSet;
import java.util.Set;

public class DatabaseClearTestExecutionListener implements TestExecutionListener {

    private static final Set<String> tables = new HashSet<>();
    private static final ThreadLocal<JdbcTemplate> jdbcTemplates = new ThreadLocal<>();

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {
        ApplicationContext ac = testContext.getApplicationContext();
        JdbcTemplate jdbcTemplate = ac.getBean(JdbcTemplate.class);
        jdbcTemplates.set(jdbcTemplate);
        initialTable();
        truncate();
    }

    private void initialTable() {
        if (tables.isEmpty()) {
            JdbcTemplate jdbcTemplate = jdbcTemplates.get();
            tables.addAll(jdbcTemplate.query("SHOW TABLES", (rs, rowNum) -> rs.getString(1)));
        }
    }

    private void truncate() {
        JdbcTemplate jdbcTemplate = jdbcTemplates.get();
        for (String table : tables) {
            jdbcTemplate.update("TRUNCATE TABLE " + table);
        }
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        truncate();
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        jdbcTemplates.remove();
    }
}
