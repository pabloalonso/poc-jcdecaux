package com.jcdecaux.rest.api;

import groovy.json.JsonBuilder

import java.util.logging.Logger

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.apache.http.HttpHeaders
import org.bonitasoft.web.extension.ResourceProvider
import org.bonitasoft.web.extension.rest.RestApiResponse
import org.bonitasoft.web.extension.rest.RestApiResponseBuilder
import com.bonitasoft.web.extension.rest.RestAPIContext
import com.bonitasoft.web.extension.rest.RestApiController
import com.jcdecaux.sapConnector.SapResponsables
/*
import com.sap.conn.jco.JCoContext
import com.sap.conn.jco.JCoDestination
import com.sap.conn.jco.JCoFunction
import com.sap.conn.jco.JCoFunctionTemplate
import com.sap.conn.jco.JCoParameterField
import com.sap.conn.jco.JCoParameterFieldIterator
import com.sap.conn.jco.JCoParameterList
import com.sap.conn.jco.JCoRepository
import com.sap.conn.jco.JCoTable
*/
class Index implements RestApiController {

    Logger logger = Logger.getLogger("org.bonitasoft");

    @Override
    RestApiResponse doHandle(HttpServletRequest request, RestApiResponseBuilder responseBuilder, RestAPIContext context) {
        // To retrieve query parameters use the request.getParameter(..) method.
        // Be careful, parameter values are always returned as String values

        // Retrieve p parameter
        def p = request.getParameter "p"
        if (p == null) {
            return buildResponse(responseBuilder, HttpServletResponse.SC_BAD_REQUEST,"""{"error" : "the parameter p is missing"}""")
        }

        // Retrieve c parameter
        def c = request.getParameter "c"
        if (c == null) {
            return buildResponse(responseBuilder, HttpServletResponse.SC_BAD_REQUEST,"""{"error" : "the parameter c is missing"}""")
        }

        // Here is an example of how you can retrieve configuration parameters from a properties file
        // It is safe to remove this if no configuration is required
        //Properties props = loadProperties("configuration.properties", context.resourceProvider)
        //String paramValue = props["myParameterKey"]

        // Prepare the result
        def result = [ "p" : p ,"c" : c ]
		//Map responsables = getResponsables(context.resourceProvider);
		SapResponsables sapResponsables = new SapResponsables();
		result = sapResponsables.getResponsables();

        // Send the result as a JSON representation
        // You may use buildPagedResponse if your result is multiple
        return buildResponse(responseBuilder, HttpServletResponse.SC_OK, new JsonBuilder(result).toString())
    }

    /**
     * Build an HTTP response.
     *
     * @param  responseBuilder the Rest API response builder
     * @param  httpStatus the status of the response
     * @param  body the response body
     * @return a RestAPIResponse
     */
    RestApiResponse buildResponse(RestApiResponseBuilder responseBuilder, int httpStatus, Serializable body) {
        return responseBuilder.with {
            withResponseStatus(httpStatus)
            withResponse(body)
            build()
        }
    }

    /**
     * Returns a paged result like Bonita BPM REST APIs.
     * Build a response with a content-range.
     *
     * @param  responseBuilder the Rest API response builder
     * @param  body the response body
     * @param  p the page index
     * @param  c the number of result per page
     * @param  total the total number of results
     * @return a RestAPIResponse
     */
    RestApiResponse buildPagedResponse(RestApiResponseBuilder responseBuilder, Serializable body, int p, int c, long total) {
        return responseBuilder.with {
            withContentRange(p,c,total)
            withResponse(body)
            build()
        }
    }


/*	
	Map getResponsables(ResourceProvider resourceProvider) {
		String functionName = "Z_GET_VERNR";
		Map output = new HashMap();
		List<String> responsables = new ArrayList();
		//String functionName = "BAPI_USER_GET_DETAIL"
		JCoDestination destination = SapConnection.getDestination("ABAP_AS_WITHOUT_POOL", resourceProvider)
		JCoRepository sapRepository;
		JCoContext.begin(destination);
		sapRepository = destination.getRepository();

		if (sapRepository == null) {			
			output.message = "Couldn't get repository!"
			logger.info(output.message);
			JCoContext.end(destination);
			System.exit(0);
		}

		JCoFunctionTemplate template = sapRepository.getFunctionTemplate(functionName);

		if (template == null) {
			output.message = "Couldn't get template!";
		} else {
			JCoFunction function = template.getFunction();
			function.execute(destination);
			output.message ="Executed function Z_GET_VERNR";
			JCoParameterList jcoParameterList = function.getTableParameterList();
			if(jcoParameterList != null) {
				JCoTable table = function.getTableParameterList().getTable("RETURN");
				if(table != null) {
					if (!table.isEmpty()) {
						// Affichage du message d'erreur SAP
						String sMessage = table.getString("MESSAGE");
						output.message = "Message: " + sMessage;
						if (sMessage.contains("does not exist"))
							output.message = "User does not exist.";
					} else {
						// Récupération des utilisateurs dans un fichier texte
						output.message = "Success";
		
						JCoParameterList jcoexptparamlist = function.getExportParameterList();
						JCoParameterFieldIterator jcoparmfielditr = jcoexptparamlist.getParameterFieldIterator();
						while (jcoparmfielditr.hasNextField()) {
							JCoParameterField jcoparamfield = jcoparmfielditr.nextParameterField();
							logger.info(jcoparamfield.getValue());
							responsables.add(jcoparamfield.getValue())
							
						}
		
					}
				}
				else {
					output.message = "function executed but output is null";
				}	
			}
			else {
				output.message = "jcoParameterList is null";
			}
		}
		output.responsables = responsables;
		JCoContext.end(destination)
		return output;
	}
*/
}
