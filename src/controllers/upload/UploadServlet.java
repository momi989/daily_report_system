package controllers.upload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import models.Report;
import utils.DBUtil;




@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    //protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //    RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
    //    rd.forward(request, response);
   // }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // EntityManagerのオブジェクトを生成
        EntityManager em = DBUtil.createEntityManager();

           Report r = em.find(Report.class, (Integer) (request.getSession().getAttribute("report_id")));


        //name属性がpictのファイルをPartオブジェクトとして取得
        Part part=request.getPart("pict");
        //ファイル名を取得
        String filename=Paths.get(part.getSubmittedFileName()).getFileName().toString();
        //アップロードするフォルダ
        String path="C:\\pleiades\\workspace\\daily_report_system\\WebContent\\upload";//getServletContext().getRealPath("/upload");
        //実際にファイルが保存されるパス確認
        System.out.println(path);


        //書き込み
        part.write(path+File.separator+filename);
                request.setAttribute("filename", filename);


        //RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
        //rd.forward(request, response);


     r.setFilename(filename);
        //r.setFilename(request.getParameter("filename"));

                //nullNoImageになる
        //r.setFilename((request.getParameter("filename")) + r.getFilename());


     // データベースを更新
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
        request.getSession().setAttribute("flush", "画像をアップしました。");


     // indexページへリダイレクト
        response.sendRedirect(request.getContextPath() + "/reports/index");
    }
}






/*

public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

     // EntityManagerのオブジェクトを生成
           EntityManager em = DBUtil.createEntityManager();

           Report r = em.find(Report.class, (Integer)(request.getSession().getAttribute("report_id")));


        Part part = request.getPart("file");
        String name = this.getFileName(part);
        part.write(getServletContext().getRealPath("/upload") + "/" + name);
        response.sendRedirect(request.getContextPath() + "/reports/index");
    }

    private String getFileName(Part part) {
        String name = null;
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
                name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
                name = name.substring(name.lastIndexOf("\\") + 1);
                break;
            }
        }
        return name;
    }
    */

