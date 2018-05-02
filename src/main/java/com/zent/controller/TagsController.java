package com.zent.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zent.dao.ITagsDAO;
import com.zent.entity.Tags;
import com.zent.json.TagsJsonObject;
import com.zent.utils.Constant;

@Controller
public class TagsController {
	private ITagsDAO tagsDAO;

	public ITagsDAO getTagsDAO() {
		return tagsDAO;
	}

	public void setTagsDAO(ITagsDAO tagsDAO) {
		this.tagsDAO = tagsDAO;
	}
	@RequestMapping(value = "/tag", method = RequestMethod.GET)
	public String index(Model model, HttpSession session) {
		return "tagsmanager";
	}
	//show list
		@RequestMapping(value = "/listtags", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
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
			List<Tags> listTags = new ArrayList<Tags>();
			Tags tag = new Tags();
			tag.setName(searchParameter);
			listTags = tagsDAO.searchTags(tag, page);

			// Here is server side pagination logic. Based on the page number you could make
			// call
			// to the data base create new list and send back to the client. For demo I am
			// shuffling
			// the same list to show data randoml

			TagsJsonObject tagsJsonObject = new TagsJsonObject();
			// Set Total display record
			if (searchParameter.equals("")) {
				tagsJsonObject.setiTotalDisplayRecords(tagsDAO.getAll().size());
				// Set Total record
				tagsJsonObject.setiTotalRecords(tagsDAO.getAll().size());
			} else {
				tagsJsonObject.setiTotalDisplayRecords(listTags.size());
				// Set Total record
				tagsJsonObject.setiTotalRecords(listTags.size());
			}
			tagsJsonObject.setAaData(listTags);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json2 = gson.toJson(tagsJsonObject);
			// System.out.println(json2);
			return json2;
		}
}
