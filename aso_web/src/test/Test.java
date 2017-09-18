import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.orm.ICommonHqlDao;
import com.ruanyun.web.dao.hardware.HardwareReportDao;
import com.ruanyun.web.dao.sys.UserDao;
import com.ruanyun.web.dao.web.ReportDao;
import com.ruanyun.web.hardware.HardwareCommandService;
import com.ruanyun.web.hardware.model.HardwareModel;
import com.ruanyun.web.model.TFactor;
import com.ruanyun.web.model.THardwareReport;
import com.ruanyun.web.model.TReport;
import com.ruanyun.web.model.web.ClassOrg;
import com.ruanyun.web.service.web.ClassInfoService;
import com.ruanyun.web.service.web.FactorLevelService;
import com.ruanyun.web.service.web.FactorService;
import com.ruanyun.web.service.web.ReportService;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml"})
//,		"classpath:spring-servlet.xml"
@Transactional
@TransactionConfiguration(transactionManager = "txManager", defaultRollback =false)
public class Test {

}
