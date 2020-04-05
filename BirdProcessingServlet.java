/* Name: Sai Manogna Pentyala
Andrew: spentyal
Task: Project 1 Task 2a*/

package com.andrew.spentyal;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet to get the input from the user, bird drop down list, and the image and photographer name of the bird
 **/
@WebServlet(name = "BirdProcessingServlet", urlPatterns = {"/getFirstInput", "/getBirdDropDown", "/getImageResult"})
public class BirdProcessingServlet extends javax.servlet.http.HttpServlet {

    BirdProcessingModel bpm = null;  // The "business model" for this app

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        bpm = new BirdProcessingModel();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        try {
            // to determine whether the application is a desktop, android or iphone application
            String ua = request.getHeader("User-Agent");

            // prepare the appropriate DOCTYPE for the view pages
            if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
                request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
            } else {
                //desktop application
                request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
            }

            /**forwarding the request object to index.jsp - comes here for the first time
             when the application is launched **/
            if (request.getServletPath().equalsIgnoreCase("/getFirstInput")) {

                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);

                //getting the image and the photographer details of the bird chosen
            } else if (request.getServletPath().equalsIgnoreCase("/getImageResult")) {

                //bird chosen by the user from the dropdown
                String birdName = request.getParameter("birdDropDownList");

                List<String> birdResultList = bpm.getBirdResultList(birdName);

                request.setAttribute("birdUrl", birdResultList.get(0)); //image url
                request.setAttribute("photographerName", birdResultList.get(1)); //photographer name
                request.setAttribute("birdName", birdName);

                //forwarding the request object to result.jsp
                RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
                dispatcher.forward(request, response);

                /**getting the dropdown list of the birds whose names have the name of the
                 input bird chosen by the user **/
            } else if (request.getServletPath().equalsIgnoreCase("/getBirdDropDown")) {
                // input bird entered by the user in the text box
                String inputBird = request.getParameter("birdName");

                if(inputBird != null && !inputBird.isEmpty() && !inputBird.isBlank()) {
                    // get the entire birds list from the zoo site
                    List<String> birdList = bpm.getBirdTotalList();
                    // get the sublist of at the most 10 birds from the zoo site for the dropdown
                    List<String> birdSubList = bpm.getBirdSubTotalList(inputBird, birdList);

                    request.setAttribute("birdTotalList", birdList); // the entire bird list
                    request.setAttribute("birdSubTotalList", birdSubList); //the list of bird names for teh dropdown
                    request.setAttribute("actualBirdName", inputBird);

                    if (birdSubList.size() == 0) {
                        //to identify the case where there are no birds in the zoo site that contains the bird name
                        //inputted by the user
                        request.setAttribute("birdSubTotalListSize", "zero");
                    }

                    // to handle the scenario when the bird name is not entered
                } else {
                    request.setAttribute("nobird", "Please Enter A Valid Bird Name !");
                }

                //forwarding the request object to dropdown.jsp
                RequestDispatcher dispatcher = request.getRequestDispatcher("dropdown.jsp");
                dispatcher.forward(request, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            //if the link to the zoo site fails
            if (e.getMessage().equalsIgnoreCase("error")) {
                //notifying the user that the request to the zoo site failed
                request.setAttribute("error", "Sorry ! Request To The Zoo Site Failed !");
                //forwarding the request object to dropdown.jsp
                RequestDispatcher dispatch = request.getRequestDispatcher("dropdown.jsp");
                dispatch.forward(request, response);
            }
        }
    }
}
