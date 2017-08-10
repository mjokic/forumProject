package servleti;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.MailDAO;
import dao.UserDAO;
import obicne.Mail;
import obicne.User;

/**
 * Servlet implementation class Panel
 */
public class Panel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Panel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		
		if (user == null) {
			request.setAttribute("message", "Please log in first");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}
		
		ArrayList<User> users = UserDAO.get();
		
		Mail mail = MailDAO.getMail();
		
		request.setAttribute("users", users);
		request.setAttribute("mail", mail);
		request.getRequestDispatcher("panel.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		
		String method = request.getParameter("action");
		
		String message;;
		String status;
		
		Map<String, Object> data = new LinkedHashMap<>();
		if("editInfo".equals(method)){
			boolean editInfoStatus = editInfo(request);
			
			if(editInfoStatus == true){
				message = "User info edited successfully!";
				status = "success";
				
			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
			
		}else if("editPass".equals(method)){
			boolean editPassStatus = editPassword(request);
			
			if(editPassStatus == true){
				message = "User password edited successfully!";
				status = "success";
				
			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
		}else if(ServletFileUpload.isMultipartContent(request)){
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = null;
			
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			
			if("editAvatar".equals(items.get(0).getString())){
				boolean editAvatarStatus = editAvatar(request, items.get(1));
				
				if(editAvatarStatus == true){
					message = "User avatar changed successfully!";
					status = "success";
				}else{
					message = "Failed uploading avatar!";
					status = "fail";
				}
				
			}else{
				message = "Something Broke! Try again!";
				status = "fail";	
			}
			
			
			
		}else if("editMember".equals(method)){
			boolean editMemberStatus = editMember(request);
			
			if(editMemberStatus == true){
				message = "Member edited successfully!";
				status = "success";
				
			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
		}else if("delMember".equals(method)){
			boolean delMemberStatus = delMember(request);
			
			if(delMemberStatus == true){
				message = "Member deleted successfully!";
				status = "success";
				
			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
		}else if("passResetMember".equals(method)){
			boolean passResetMemberStatus = passResetMember(request);
			
			if(passResetMemberStatus == true){
				message = "Member password reset successfully!";
				status = "success";
				
			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
			

		}else if("banMember".equals(method)){
			boolean banMemberStatus = banMember(request);
			String wut = request.getParameter("banned");
			
			if(banMemberStatus == true){
				if("true".equals(wut)){
					message = "Member banned successfully!";
				}else{
					message = "Member unbanned successfully!";	
				}
				
				status = "success";
				
			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
		}else if("actMember".equals(method)){
			boolean actMemberStatus = actMember(request);
			String wut = request.getParameter("active");
			
			if(actMemberStatus == true){
				if("true".equals(wut)){
					message = "Member activated successfully!";
				}else{
					message = "Member deactivated successfully!";	
				}
				
				status = "success";
				
			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
		}else if("editMailSet".equals(method)){
			boolean editMailSetStatus = editMailSet(request);
			
			if(editMailSetStatus == true){
				message = "Settings changed successfully!";
				status = "success";
				
			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
		
		}else if("testMailSet".equals(method)){
			boolean testMailSetStatus = testMailSet(request);
			
			if(testMailSetStatus == true){
				message = "Mail sent!";
				status = "success";
				
			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
		}else if("editConfMessage".equals(method)){
			boolean editConfMessStatus = editConfMess(request);
			
			if(editConfMessStatus == true){
				message = "Message edited succesfully!";
				status = "success";
				
			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
			
		}else{

			
			message = "Server is confused! Try again!";
			status = "fail";
		}
		
		data.put("message", message);
		data.put("status", status);
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.getWriter().write(jsonData);
		
	}
	
	
	private boolean editInfo(HttpServletRequest request){
		boolean status = false;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null){
			return status;
		}
		
		
		String newEmail = request.getParameter("email");
		String newName = request.getParameter("fname");
		String newLname = request.getParameter("lname");
		String pageNum_tmp = request.getParameter("pageNum");
		
		System.out.println(newEmail + " " + newName + " " + newLname + " " + pageNum_tmp);
		
		int pageNum = 0;
		try {
			List<Integer> lista = Arrays.asList(5, 10, 20);
			
			pageNum = Integer.parseInt(pageNum_tmp);
			
			if(newEmail.isEmpty() || !lista.contains(pageNum)) throw new Exception();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		user.setEmail(newEmail);
		user.setName(newName);
		user.setSurname(newLname);
		user.setNumberPerPage(pageNum);
		
		if(UserDAO.update(user)) status = true;
		
		
		return status;
	}
	
	

	private boolean editPassword(HttpServletRequest request){
		boolean status = false;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null){
			return status;
		}
		
		
		String oldPassword = request.getParameter("oldPassword");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		
		if(!password1.equals(password2) && password1.length() < 8) return status;
		

		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-512");
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return status;
		}
		byte[] hashOldPass = digest.digest(oldPassword.getBytes());
		byte[] hashNewPass = digest.digest(password1.getBytes());
		
		String hashedOldPassword = DatatypeConverter.printHexBinary(hashOldPass);
		String hashedNewPassword = DatatypeConverter.printHexBinary(hashNewPass);
		
		
		if(!hashedOldPassword.equals(user.getPassword())) return status;
		
		user.setPassword(hashedNewPassword);
		
		if(UserDAO.update(user)){
			status = true;
			logoutMember(user.getId());
		}
		
		
		return status;
	}


	private boolean editAvatar(HttpServletRequest request, FileItem avatar){
		boolean status = false;
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null){
			return status;
		}

		// parametar mora biti 'avatar'
		if(!"avatar".equals(avatar.getFieldName())) return status;
		
		// provera da li je uploadovani fajl stvarno slika
		String avatarContentType = avatar.getContentType();
		List<String> lista = Arrays.asList("image/png", "image/jpeg", "image/jpg");
		if(!lista.contains(avatarContentType)) return status;
		
		String[] tmp = avatarContentType.split("/");
		String type = tmp[1];
		

		// velicina ne moze biti veca od 100kb
		if(avatar.getSize() > 100001) return status;

		String path = getServletContext().getRealPath("assets");
		System.out.println(path);
		
		String uniqueAvatarName = String.valueOf(new Date().getTime()) + "." + type;
		System.out.println(uniqueAvatarName);
		
		File f = new File(path + "/images/avatars/" + uniqueAvatarName);
		System.out.println(f.getAbsolutePath());
		try {
			avatar.write(f);
			
			user.setAvatarName(uniqueAvatarName);
			UserDAO.update(user);
			
			ServletContext context = getServletContext();
			ArrayList<User> users = UserDAO.get();
	    	context.setAttribute("users", users);
			
			status = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return status;
	}
	

	private boolean editMember(HttpServletRequest request){
		boolean status = false;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null || user.getRole().getId() != 1){
			return status;
		}
		
		
		String tmp_id = request.getParameter("id");
		String tmp_roleId = request.getParameter("roleId");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String fName = request.getParameter("fname");
		String lName = request.getParameter("lname");
		
		int id;
		int roleId;
		try {
			id = Integer.parseInt(tmp_id);
			roleId = Integer.parseInt(tmp_roleId);
			
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		if(email.isEmpty() || username.isEmpty()) return status;
		
		
		if(UserDAO.updateMemberInfo(id, roleId, email, username, fName, lName)){
			status = true;
			logoutMember(id);
		}
		
		
		return status;
	}

	
	private boolean banMember(HttpServletRequest request){
		boolean status = false;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null || user.getRole().getId() != 1){
			return status;
		}
		
		
		String tmp_id = request.getParameter("id");
		String tmp_banned = request.getParameter("banned");
		
		
		int id;
		boolean banned;
		try {
			id = Integer.parseInt(tmp_id);
			banned = Boolean.parseBoolean(tmp_banned);
			
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		
		if(UserDAO.updateBanMember(id, banned)){
			status = true;
			logoutMember(id);
		}
		
		
		return status;
	}


	private boolean delMember(HttpServletRequest request){
		boolean status = false;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null || user.getRole().getId() != 1){
			return status;
		}
		
		
		String tmp_id = request.getParameter("id");

		int id;
		try {
			id = Integer.parseInt(tmp_id);
			
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		if(UserDAO.deleteMember(id)){
			status = true;
			logoutMember(id);
		}
		
		
		return status;
	}


	private boolean passResetMember(HttpServletRequest request){
		boolean status = false;

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("user");
		
		if(loggedInUser == null || loggedInUser.getRole().getId() != 1){
			return status;
		}
		
		
		String tmp_id = request.getParameter("id");

		int id;
		try {
			id = Integer.parseInt(tmp_id);
			
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		// reset password here...
		User user = UserDAO.get(id);
		user.setPassword("password reset");
		if(UserDAO.update(user)){
			if(PasswordReset.sendReset(user)){
				status = true;
				logoutMember(user.getId());
			}
		}
		
		
		return status;
	}
	

	private boolean actMember(HttpServletRequest request){
		boolean status = false;

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null || user.getRole().getId() != 1){
			return status;
		}
		
		
		String tmp_id = request.getParameter("id");
		String tmp_active = request.getParameter("active");
		
		
		int id;
		boolean active;
		try {
			id = Integer.parseInt(tmp_id);
			active = Boolean.parseBoolean(tmp_active);
			
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		
		if(UserDAO.updateActMember(id, active)){
			status = true;
			logoutMember(id);
		}
		
		
		return status;
	}

	
	private boolean logoutMember(int memberId){
		boolean status = false;
		HttpSession session = null;
		
		
		HashMap<String,HttpSession> sessije = (HashMap<String, HttpSession>) SessionListener.getSessions();
		
		for (String s : sessije.keySet()) {
			
			session = sessije.get(s);
			
			User u = (User) session.getAttribute("user");
			if(u != null){
				
				if(u.getId() == memberId){
					break;
				}else{
					session = null;
				}
	
			}
		
		}
		
		if(session != null){
			session.invalidate();
			status = true;
		}
		
		return status;
	}
	
	
	private boolean editMailSet(HttpServletRequest request){
		boolean status = false;
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null || user.getRole().getId() != 1){
			return status;
		}
		
		String host = request.getParameter("host");
		String port = request.getParameter("port");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(host.isEmpty() || port.isEmpty()) return status;
		
		
		Mail mail = new Mail(host, port, username, password);
		if(MailDAO.updateMail(mail)) status = true;
		
		
		return status;
	}
	
	
	private boolean testMailSet(HttpServletRequest request){
		boolean status = false;
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null || user.getRole().getId() != 1){
			return status;
		}
		
		String host = request.getParameter("host");
		String port = request.getParameter("port");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String to = request.getParameter("to");
		
		if(host.isEmpty() || port.isEmpty()) return status;
		
		Mail mail = new Mail(host, port, username, password);
		mail.setSubject("Email Test");
		mail.setContent("<b>If you've received this email everything is setup correctly.</b>");
		
		if(mail.sendMail(to)) status = true;
		
		return status;
	}
	
	
	private boolean editConfMess(HttpServletRequest request){
		boolean status = false;
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null || user.getRole().getId() != 1){
			return status;
		}
		
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		if(subject.isEmpty() || content.isEmpty()) return status;

		if(MailDAO.updateConfMessageDetails(subject, content)) status = true;
		
		return status;
	}
	
	
}
