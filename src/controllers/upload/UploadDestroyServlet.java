package controllers.upload;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controllers.upload.UploadServlet;
import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class UploadDestroyServlet
 */
@WebServlet("/UploadDestroyServlet")
public class UploadDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadDestroyServlet() {
        super();
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String _token = request.getParameter("_token");
        //if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Report r = em.find(Report.class, (Integer) (request.getSession().getAttribute("report_id")));

            //r.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            String filename=r.getFilename();
            //request.setAttribute("filename", filename);

            //ファイル名を直接指定すると消える
            File file = new File("C:\\pleiades\\workspace\\daily_report_system\\WebContent\\upload\\" + filename);
            file.delete();


            r.setFilename("NoImage");

            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "削除が完了しました。");

            response.sendRedirect(request.getContextPath() + "/employees/index");
        }
    }

