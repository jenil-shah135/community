package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.AddressBean;
import com.bean.CityBean;
import com.bean.CountryBean;
import com.bean.FamilyBean;
import com.bean.StateBean;
import com.dao.AddressDAO;
import com.dao.CityDAO;
import com.dao.CountryDAO;
import com.dao.FamilyDAO;
import com.dao.StateDAO;
import com.utility.General;
import com.utility.Validations;

public class ChangeAddressServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;  
    public ChangeAddressServlet()
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		CountryDAO objCountryDAO=new CountryDAO();
		request.setAttribute("countryList", objCountryDAO.getAll());
		request.setAttribute("currentStateList", new ArrayList<StateBean>());
		request.setAttribute("currentCityList", new ArrayList<CityBean>());		
		HttpSession session = request.getSession(true);
		String msg=General.GetMessage(session);
		if(msg!=null)
		{
			Map<String,String> messages=new HashMap<String,String>();
			messages.put("success", msg);
			request.setAttribute("messages", messages);
		}
		if(session!=null)
		{
			Integer familyID=(Integer)session.getAttribute("familyID");		
			if(familyID!=null)
			{
				FamilyDAO objFamilyDAO=new FamilyDAO();
				FamilyBean objFamilyBean=objFamilyDAO.getByID(familyID);
				if(objFamilyBean!=null)
				{
					request.setAttribute("nativeAddress", objFamilyBean.getNativeAddress());					
					request.setAttribute("currentAddressStreet", objFamilyBean.getCurrentAddress().getStreet());
					request.setAttribute("currentAddressArea", objFamilyBean.getCurrentAddress().getArea());
					request.setAttribute("currentAddressPincode", objFamilyBean.getCurrentAddress().getPincode());
					if(objFamilyBean.getCurrentAddress().getCity()!=null)
					{
						request.setAttribute("currentAddressCity", objFamilyBean.getCurrentAddress().getCity().getCityID());
						if(objFamilyBean.getCurrentAddress().getCity().getState()!=null)
						{
							request.setAttribute("currentAddressState", objFamilyBean.getCurrentAddress().getCity().getState().getStateID());
							request.setAttribute("currentCityList", new CityDAO().getAllByStateID(objFamilyBean.getCurrentAddress().getCity().getState().getStateID()));
							if(objFamilyBean.getCurrentAddress().getCity().getState().getCountry()!=null)
							{
								request.setAttribute("currentAddressCountry", objFamilyBean.getCurrentAddress().getCity().getState().getCountry().getCountryID());
								request.setAttribute("currentStateList", new StateDAO().getAllByCountryID(objFamilyBean.getCurrentAddress().getCity().getState().getCountry().getCountryID()));
							}
						}
					}
					RequestDispatcher rd = request.getRequestDispatcher("/User/ChangeAddress.jsp");
					rd.forward(request, response);
				}
				else
				{
					response.sendRedirect("/Community/");
				}
			}
			else
			{
				response.sendRedirect("/Community/");
			}
		}
		else
		{
			response.sendRedirect("/Community/");
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);
		CountryDAO objCountryDAO=new CountryDAO();
		StateDAO objStateDAO=new StateDAO();
		CityDAO objCityDAO=new CityDAO();
		AddressDAO objAddressDAO=new AddressDAO();
		FamilyDAO objFamilyDAO=new FamilyDAO();
		
		FamilyBean objFamilyBean=null;
		
		request.setAttribute("countryList", objCountryDAO.getAll());
		request.setAttribute("currentStateList", new ArrayList<StateBean>());
		request.setAttribute("currentCityList", new ArrayList<CityBean>());	
		Map<String,String> messages=new HashMap<String,String>();
		try
		{
			String currentAddressStreet=request.getParameter("currentAddressStreet");
			String currentAddressArea=request.getParameter("currentAddressArea");
			String currentAddressCity=request.getParameter("currentAddressCity");
			String currentAddressState=request.getParameter("currentAddressState");
			String currentAddressCountry=request.getParameter("currentAddressCountry");
			String currentAddressPincode=request.getParameter("currentAddressPincode");
			String nativeAddress=request.getParameter("nativeAddress");
			
			if(currentAddressStreet==null)
			{
				currentAddressStreet="";
			}
			if(currentAddressArea==null)
			{
				currentAddressArea="";
			}
			if(currentAddressCountry==null)
			{
				messages.put("currentAddressCountryError", "<div class='editor-error'>Please select your current country.</div>");
			}
			else
			{
				currentAddressCountry=currentAddressCountry.trim();
				if(currentAddressCountry.length()==0)
				{
					messages.put("currentAddressCountryError", "<div class='editor-error'>Please select your current country.</div>");
				}
				else
				{
					if(!Validations.isNumeric(currentAddressCountry))
					{
						messages.put("currentAddressCountryError", "<div class='editor-error'>Please select your current country.</div>");
					}
					else
					{
						CountryBean objCurrentCountry=objCountryDAO.getByID(Integer.parseInt(currentAddressCountry));
						if(objCurrentCountry==null)
						{
							messages.put("currentAddressCountryError", "<div class='editor-error'>Please select your current country.</div>");
						}
						else
						{
							request.setAttribute("currentStateList", objStateDAO.getAllByCountryID(Integer.parseInt(currentAddressCountry)));	
						}
					}
				}
			}
			if(currentAddressState==null)
			{
				messages.put("currentAddressStateError", "<div class='editor-error'>Please select your current state.</div>");
			}
			else
			{
				currentAddressState=currentAddressState.trim();
				if(currentAddressState.length()==0)
				{
					messages.put("currentAddressStateError", "<div class='editor-error'>Please select your current state.</div>");
				}
				else
				{
					if(!Validations.isNumeric(currentAddressState))
					{
						messages.put("currentAddressStateError", "<div class='editor-error'>Please select your current state.</div>");
					}
					else
					{
						StateBean objCurrentState=objStateDAO.getByID(Integer.parseInt(currentAddressState));
						if(objCurrentState==null)
						{
							messages.put("currentAddressStateError", "<div class='editor-error'>Please select your current state.</div>");
						}
						else
						{
							request.setAttribute("currentCityList", objCityDAO.getAllByStateID(Integer.parseInt(currentAddressState)));	
						}
					}
				}
			}
			if(currentAddressCity==null)
			{
				messages.put("currentAddressCityError", "<div class='editor-error'>Please enter your current city.</div>");
			}
			else
			{
				currentAddressCity=currentAddressCity.trim();
				if(currentAddressCity.length()==0)
				{
					messages.put("currentAddressCityError", "<div class='editor-error'>Please select your current city.</div>");
				}
				else
				{
					if(!Validations.isNumeric(currentAddressCity))
					{
						messages.put("currentAddressCityError", "<div class='editor-error'>Please select your current city.</div>");
					}
					else
					{
						CityBean objCurrentCity=objCityDAO.getByID(Integer.parseInt(currentAddressCity));
						if(objCurrentCity==null)
						{
							messages.put("currentAddressCityError", "<div class='editor-error'>Please select your current city.</div>");
						}
					}
				}
			}			
			if(currentAddressPincode==null)
			{
				currentAddressPincode="";
			}
			else
			{
				currentAddressPincode=currentAddressPincode.trim();
				if(currentAddressPincode.length()!=0)
				{
					if(!Validations.isNumeric(currentAddressPincode))
					{
						messages.put("currentAddressPincodeError", "<div class='editor-error'>Please enter valid current address pincode.</div>");
					}
				}
			}
			if(nativeAddress==null)
			{
				nativeAddress="";
			}			
			if(session!=null)
			{
				Integer familyID=(Integer)session.getAttribute("familyID");		
				if(familyID!=null)
				{
					objFamilyBean=objFamilyDAO.getByID(familyID);
				}
			}
			if(objFamilyBean==null)
			{
				messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
			}
			if(messages.size()==0)
			{
				CityBean objCurrentCity=objCityDAO.getByID(Integer.parseInt(currentAddressCity));
				if(!objCurrentCity.getState().getStateID().toString().equals(currentAddressState))
				{
					messages.put("currentAddressStateError", "<div class='editor-error'>Please select your current state.</div>");
				}
				else
				{
					if(!objCurrentCity.getState().getCountry().getCountryID().toString().equals(currentAddressCountry))
					{
						messages.put("currentAddressCountryError", "<div class='editor-error'>Please select your current country.</div>");
					}
				}
				if(messages.size()==0)
				{
					AddressBean objCurrentAddress=objFamilyBean.getCurrentAddress();
					objCurrentAddress.setStreet(currentAddressStreet);
					objCurrentAddress.setArea(currentAddressArea);
					objCurrentAddress.setCity(objCurrentCity);
					objCurrentAddress.setPincode(currentAddressPincode);
					objAddressDAO.update(objCurrentAddress);
					objFamilyBean.setCurrentAddress(objCurrentAddress);
					objFamilyBean.setNativeAddress(nativeAddress);
					if(!objFamilyDAO.update(objFamilyBean))
					{
						messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
					}
				}
			}
		}
		catch(Exception ex)
		{
			messages.put("mainError", "<div class='editor-error'>Something went wrong.</div>");
		}
		finally
		{
			if(messages.size()==0)
			{
				session.setAttribute("msg", "<div class='editor-success'>Address successfully updated.</div>");
				response.sendRedirect("/Community/User/Address/Update");
			}
			else
			{
				request.setAttribute("messages", messages);
				RequestDispatcher rd = request.getRequestDispatcher("/User/ChangeAddress.jsp");
				rd.forward(request, response);
			}
		}
	}
}