package org.ewell.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.ewell.model.ChooseModel;
import org.ewell.service.FooBarService;
import org.ewell.service.scale.VoterService;
import org.ewell.vo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/users")
public class UserResource {
	@Autowired
	private VoterService voterService;
	@Autowired
	FooBarService fooBarService;

	@GET
	@Path("{chooseItem}")
	public Response deleteItem(@PathParam("chooseItem") String chooseItem) {

		System.out.println("chooseItem is " + chooseItem);
		return Response.status(200).entity(chooseItem).build();
	}

	@GET
	public Response getRatios() {
		// voterService.getVoters();
		String jsonStr = generateJsonEmployee();
		System.out.println("jsonStr " + jsonStr);
		return Response.status(200).entity(jsonStr).build();
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response chosseOne(@FormParam("chooseVal") String chooseVal) {
		ChooseModel model = new ChooseModel();
		model.setChooseCount(1);
		model.setChooseItem(chooseVal);
		voterService.addVote(model);
		return Response.status(200).entity(chooseVal).build();
	}

	/**
	 * 
	 * @return
	 */
	private String generateJsonEmployee() {
		ObjectMapper mapper = new ObjectMapper();
		Employee e1 = new Employee();
		e1.setAge("<30");
		e1.setPercentage(10);
		e1.setGrowing(35);
		Employee e2 = new Employee();
		e2.setAge("30-40");
		e2.setPercentage(40);
		e2.setGrowing(30);
		Employee e3 = new Employee();
		e3.setAge("40-50");
		e3.setPercentage(30);
		e3.setGrowing(30);
		Employee e4 = new Employee();
		e4.setAge("50+");
		e4.setPercentage(20);
		e4.setGrowing(30);

		List<Employee> items = new ArrayList<Employee>();
		items.add(e1);
		items.add(e2);
		items.add(e3);
		items.add(e4);

		String jsonStr;
		try {
			jsonStr = mapper.writeValueAsString(items);
		} catch (Exception e) {
			return "";
		}
		return jsonStr;
	}

	public static void main(String[] args) {
		// String value = generateJsonEmployee();
		// System.out.println(value);
	}

	@GET
	@Path("type")
	public Response redirectTypePage() {
		URI unslashed = URI
				.create("examples/chapter08/pagesExt/bookTypeList.jsp");
		return Response.seeOther(unslashed).build();

	}
}
