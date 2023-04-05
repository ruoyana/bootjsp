package com.mycs.controller;


import com.mycs.entity.User;
import com.mycs.service.Userservice;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Transactional
@Controller
public class Userhandlers {

    public String conf_filename = System.getProperty("user.dir") + "\\src\\main\\resources\\fdfs_client.conf";   //文件路径指向使用



    @Autowired
    private Userservice userdao;


    @RequestMapping(value = "/query", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String query(Map<String, Object> map, Locale locale, String pageNo, String pageSize) {


        if (pageNo == null || pageNo.equals("")) {
            pageNo = "0";
        }

        if (pageSize == null || pageSize.equals("")) {
            pageSize = "3";
        }

        map.put("sizes", Integer.valueOf(pageSize));


        map.put("querys", userdao.queryUser(Integer.valueOf(pageNo), Integer.valueOf(pageSize)));

        map.put("limits", Integer.valueOf(pageNo));

        map.put("sum", userdao.sums(Integer.valueOf(pageSize)));


        return "qu";

    }

    @RequestMapping(value = "/xiaye", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String xiaye(Map<String, Object> map, Locale locale, String pageNo, String pageSize) {

        map.put("sizes", Integer.valueOf(pageSize));
        map.put("limits", Integer.valueOf(pageNo) + 1);
        map.put("querys", userdao.queryUser(Integer.valueOf(pageNo) + 1, Integer.valueOf(pageSize)));

        map.put("sum", userdao.sums(Integer.valueOf(pageSize)));


        return "qu";

    }

    @RequestMapping(value = "/shangye", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String shangye(Map<String, Object> map, Locale locale, String pageNo, String pageSize) {

        map.put("sizes", Integer.valueOf(pageSize));
        map.put("limits", Integer.valueOf(pageNo) - 1);
        map.put("querys", userdao.queryUser(Integer.valueOf(pageNo) - 1, Integer.valueOf(pageSize)));

        map.put("sum", userdao.sums(Integer.valueOf(pageSize)));


        return "qu";

    }

    @RequestMapping(value = "/weiye", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String weiye(Map<String, Object> map, Locale locale, String pageNo, String pageSize) {

        map.put("sizes", Integer.valueOf(pageSize));
        map.put("limits", Integer.valueOf(pageNo) - 1); //
        map.put("querys", userdao.queryUser(Integer.valueOf(pageNo) - 1, Integer.valueOf(pageSize)));

        map.put("sum", userdao.sums(Integer.valueOf(pageSize)));


        return "qu";

    }

    @RequestMapping("/eupload")
    public String eupload(@RequestParam("file1") MultipartFile[] multipartFiles) throws IOException {

        for (MultipartFile multipartFile : multipartFiles) {


            String name = multipartFile.getOriginalFilename();

            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\win10\\Desktop\\�ļ�����\\testfile\\" + name);

            InputStream inputStream = multipartFile.getInputStream();

            int len;
            byte[] b = new byte[1024];
            while ((len = inputStream.read(b)) != -1) {
                fileOutputStream.write(b, 0, len);
            }
            inputStream.close();
            fileOutputStream.close();

        }
        return "redirect:query";
    }

    @Autowired
    private MessageSource messageSource;

    @GetMapping("show")
    public String show(ModelMap model, Locale locale) {
        String wel = messageSource.getMessage("welcome", null, locale);
        model.addAttribute("wel", wel);
        return "i18message";
    }


    public synchronized String getName(String type) {
        return System.currentTimeMillis() + "." + type;
    }

    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    public String addsave(@Validated @ModelAttribute(value = "as") User user, BindingResult bindingResult, @RequestParam("fileHead") MultipartFile multipartFile, Map<String, Object> map) throws Exception {

        ClientGlobal.init(conf_filename);  //初始化配置文件

        TrackerClient trackerClient = new TrackerClient();   //fastdfs追踪客户端


        TrackerServer trackerServer = trackerClient.getConnection();  //fastdfs追踪服务端

        StorageClient storageClient = new StorageClient(trackerServer, null);


        if (bindingResult.getErrorCount() > 0) {
            List<FieldError> list = bindingResult.getFieldErrors();

            for (FieldError fieldError : list) {
                System.out.println(fieldError.getDefaultMessage());

            }

            Map<String, String> xingbei = new HashMap();
            xingbei.put("0", " 男 ");
            xingbei.put("1", "女");

            map.put("xingbei", xingbei);

            return "add";

        }


        String name = multipartFile.getOriginalFilename();
        System.out.println("name == " + name);
        if (name != null && !name.equals("")) {

            String strs[] = name.split("\\.");
            String type = strs[strs.length - 1]; //获取到图片的后缀jpg

            String sj[] = storageClient.upload_file(multipartFile.getBytes(), type, null);

            System.out.println("新增group== " + sj[0] + " " + "新增的Id==" + sj[1]);
            user.setGrs(sj[0]);
            user.setHead(sj[1]);
        }
        userdao.add(user);

        return "redirect:/query";
    }

    //		让图片显示
    @RequestMapping("/xiazaizai")
    public void xiazai(HttpServletResponse response, @RequestParam("path") String path, @RequestParam("grs") String group) throws Exception {

        //先上传到fastdfs 然后 获取
        if (path != null && !path.equals("")) {
            System.out.println("1次");
            ClientGlobal.init(conf_filename);  //初始化配置文件

            TrackerClient trackerClient = new TrackerClient();   //fastdfs追踪客户端


            TrackerServer trackerServer = trackerClient.getConnection();  //fastdfs追踪服务端

            StorageClient storageClient = new StorageClient(trackerServer, null);

            String realPath = path;

            byte sc[] = storageClient.download_file(group, realPath); //下载 且 获取到了他的字节长度

            response.setContentType("text/plain;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment/fileName=" + realPath);

            ServletOutputStream os = response.getOutputStream();
            IOUtils.write(sc, os);


            os.close();

        }


    }


    @RequestMapping(value = "/emp", method = RequestMethod.GET)
    public String add(Map<String, Object> map) {

        Map<String, String> xingbei = new HashMap();
        xingbei.put("0", "男");
        xingbei.put("1", "女");

        map.put("xingbei", xingbei);


        map.put("as", new User());
        return "add";
    }

    //
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") int id, @RequestParam("pageNo") String pageNo, @RequestParam("pageSize") String pageSize) throws IOException, MyException {


        User user = userdao.queryid(id);


        if (user.getGrs()!= null && !user.getGrs().equals("")){
            ClientGlobal.init(conf_filename);
            TrackerClient tracker = new TrackerClient();

            // 获取跟踪器连接
            TrackerServer trackerServer = tracker.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer, null);
            storageClient.delete_file(user.getGrs(), user.getHead());
        }

        //删除指定的fastdfs文件



        userdao.delete(id);


        int pageno = Integer.valueOf(pageNo);
        int pagesize = Integer.valueOf(pageSize);

        int sum = userdao.qushangye(pageno, pagesize);


        return "redirect:/query" + "?pageNo=" + sum + "&pageSize=" + pagesize;

    }

    @RequestMapping(value = "/emp/{id}", produces = "text/html;charset=utf-8", method = RequestMethod.GET)
    public String quid(@PathVariable("id") int id, Map<String, Object> map) {

        User user = userdao.queryid(id);
        System.out.println(user);
        map.put("as", user);


        Map<String, String> xingbei = new HashMap();
        xingbei.put("0", "男");
        xingbei.put("1", "女");
        map.put("xingbei", xingbei);


        return "add";
    }

    //
//	
    @RequestMapping(value = "/emp", method = RequestMethod.PUT)
    public String updatesave(User user, @RequestParam(value = "fileHead") MultipartFile multipartFile) throws Exception {

        //先删除 后上传保存的

        ClientGlobal.init(conf_filename);
        TrackerClient tracker = new TrackerClient();

        // 获取跟踪器连接
        TrackerServer trackerServer = tracker.getConnection();

        StorageClient storageClient = new StorageClient(trackerServer, null);

        //删除指定的fastdfs文件
        storageClient.delete_file(user.getGrs(), user.getHead());


        InputStream inputStream = multipartFile.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);


        

        String name = multipartFile.getOriginalFilename();
        String strs[] = name.split("\\.");
        String type = strs[strs.length - 1]; //获取到图片的后缀jpg

        String sj[] = storageClient.upload_file(bytes, type, null);

        user.setGrs(sj[0]);
        user.setHead(sj[1]);


        userdao.update(user);
        return "redirect:/query";


    }

    //
//	
//	
    @ModelAttribute
    public void getEmployee(@RequestParam(value = "id", required = false) Integer id,
                            Map<String, Object> map) {
        if (id != null) {
            map.put("user", userdao.queryid(id));
        }
    }
//	
//


    @RequestMapping(value = "qws", method = RequestMethod.POST)
    public String Exces() {

        return "dlexcels";
    }


    @SuppressWarnings("deprecation")
    public String getCellValue(Cell cell) {


        if (cell == null)
            return "";


        if (cell.getCellTypeEnum() == CellType.STRING) {


            return cell.getStringCellValue();


        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {


            return String.valueOf(cell.getBooleanCellValue());


        } else if (cell.getCellTypeEnum() == CellType.FORMULA) {


            return cell.getCellFormula();


        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {


            return String.valueOf(cell.getNumericCellValue());


        }
        return "";
    }


}
