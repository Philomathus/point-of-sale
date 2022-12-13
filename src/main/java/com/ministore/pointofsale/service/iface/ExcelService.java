package com.ministore.pointofsale.service.iface;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExcelService {

    void export(Class clazz, List list, HttpServletResponse response);

}
