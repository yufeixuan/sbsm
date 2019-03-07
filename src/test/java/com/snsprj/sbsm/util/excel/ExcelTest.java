package com.snsprj.sbsm.util.excel;

import com.snsprj.sbsm.utils.excel.ExcelImportUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ExcelTest {

    @Test
    public void testImportExcel() {

        String filePath = "E:/temp/deptData.xlsx";

        List<Object> deptList = ExcelImportUtil.importExcel(filePath, DepartmentDTO.class);

        log.info("", deptList);
    }

}
