package com.zent.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zent.dao.ICategoriesDAO;
import com.zent.dao.IPostsDAO;
import com.zent.entity.Category;
import com.zent.entity.FormDataWithFile;
import com.zent.entity.Posts;
import com.zent.json.CategoriesJsonObject;
import com.zent.json.PostsJsonObject;
import com.zent.utils.Constant;
import com.zent.validator.PostsValidator;

@Controller
public class PostsController {
	private IPostsDAO postsDAO;
	private ICategoriesDAO categoriesDAO;
	private PostsValidator postsValidator;
	
	public PostsValidator getPostsValidator() {
		return postsValidator;
	}

	public void setPostsValidator(PostsValidator postsValidator) {
		this.postsValidator = postsValidator;
	}

	public ICategoriesDAO getCategoriesDAO() {
		return categoriesDAO;
	}

	public void setCategoriesDAO(ICategoriesDAO categoriesDAO) {
		this.categoriesDAO = categoriesDAO;
	}

	public IPostsDAO getPostsDAO() {
		return postsDAO;
	}

	public void setPostsDAO(IPostsDAO postsDAO) {
		this.postsDAO = postsDAO;
	}

	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public String indexPost(Model model, HttpSession session) {
		return "postmanager";

	}

	// post add
	@RequestMapping(value = "/post/add", method = RequestMethod.GET)
	public String index(Model model, HttpSession session) {
		model.addAttribute("posts", new Posts());
		model.addAttribute("listCate", categoriesDAO.getAll());
		model.addAttribute("listUsed", categoriesDAO.getMostUserCate());
		return "postadd";
	}
	 @InitBinder
	   protected void initBinder(WebDataBinder binder) {
	      binder.addValidators(postsValidator);
	 }
	@RequestMapping(value = "/post/add", method = RequestMethod.POST)
	public String index1(@ModelAttribute @Validated Posts  Posts ,BindingResult result,ModelMap modelMap,Model model) {
		modelMap.addAttribute("formDataWithFile", Posts);
		if(result.hasErrors()) {
			model.addAttribute("listCate", categoriesDAO.getAll());
			model.addAttribute("listUsed", categoriesDAO.getMostUserCate());
			return "postadd";
		}else {
			if(Posts.getThumbnail().getOriginalFilename()!=null && Posts.getThumbnail().getOriginalFilename()!=""&& Posts.getThumbnail().getSize()>0) {
				Posts.setNameThumbnail(Posts.getThumbnail().getOriginalFilename());
				InputStream ip = Posts.getThumbnail().getInputStream();
				OutputStream outStream = new FileOutputStream(Contants.PATH+file.getOriginalFilename());
				byte[] buffer = new byte[8*1024];
				int bytesRead;
				while((bytesRead = ip.read(buffer))!=-1) {
					outStream.write(buffer, 0, bytesRead);
				}
				outStream.close();
			return "redirect:/post";
		}
		
		
	}

	// show list
	@RequestMapping(value = "/listposts", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String springPaginationDataTables(HttpServletRequest request) throws IOException {

		// Fetch the page number from client
		Integer pageNumber = 0;
		if (null != request.getParameter("iDisplayStart"))
			pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart")) / 10) + 1;

		// Fetch search parameter
		String searchParameter = request.getParameter("sSearch");

		// Fetch Page display length
		Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
		Constant.pageSize = pageDisplayLength;
		Integer iDisplayStart = Integer.valueOf(request.getParameter("iDisplayStart"));
		Integer page = (iDisplayStart / pageDisplayLength) + 1;
		// Create page list data
		List<Posts> listPost = new ArrayList<Posts>();
		Posts post = new Posts();
		post.setTitle(searchParameter);
		listPost = postsDAO.search(post, page);

		// Here is server side pagination logic. Based on the page number you could make
		// call
		// to the data base create new list and send back to the client. For demo I am
		// shuffling
		// the same list to show data randoml

		PostsJsonObject postsJsonObject = new PostsJsonObject();
		// Set Total display record
		if (searchParameter.equals("")) {
			postsJsonObject.setiTotalDisplayRecords(postsDAO.getAll().size());
			// Set Total record
			postsJsonObject.setiTotalRecords(postsDAO.getAll().size());
		} else {
			postsJsonObject.setiTotalDisplayRecords(listPost.size());
			// Set Total record
			postsJsonObject.setiTotalRecords(listPost.size());
		}
		postsJsonObject.setAaData(listPost);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(postsJsonObject);
		// System.out.println(json2);
		return json2;
	}

	@RequestMapping(value = "/displaythumbnail/{id}", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response, @PathVariable("id") Integer id) throws IOException {
		String name = postsDAO.getPathThumbnail(id);
		File file = new File(Constant.PATH + name);
		response.setContentType("image/*");
		response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
		BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());

		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		while ((bytesRead = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		outStream.flush();
		inStream.close();
	}
}
