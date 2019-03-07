package com.snsprj.sbsm.util.excel;

import com.snsprj.sbsm.utils.excel.annotation.ExcelField;
import com.snsprj.sbsm.utils.excel.annotation.ExcelSheet;

@ExcelSheet(name = "department")
public class DepartmentDTO {

    @ExcelField(name = "部门id")
    private long deptId;

    @ExcelField(name = "部门名称")
    private String deptName;

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
