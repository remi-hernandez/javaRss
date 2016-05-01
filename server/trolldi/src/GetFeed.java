import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.zip.GZIPInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;

import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndLink;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;

@Path("/feed")
public class GetFeed {
	public JSONArray toJSON(SyndFeed feed) {
		JSONArray arr = new JSONArray();
		
		for (SyndEntry entrie : feed.getEntries())
		{
			JSONObject jsonEntrie = new JSONObject();
			jsonEntrie.putOpt("title", entrie.getTitle());
			
			JSONArray linkLst = new JSONArray();
			
			for (SyndLink link : entrie.getLinks())
			{
				linkLst.put(link.getHref());
			}
			
			jsonEntrie.putOpt("links", linkLst);
			jsonEntrie.putOpt("description", entrie.getDescription().toString());
			
			if (entrie.getAuthors() != null)
			{
				JSONArray arrPerson = new JSONArray();
				
				for (SyndPerson person : feed.getAuthors())
				{
					JSONObject jsonPerson = new JSONObject();
					jsonPerson.put("name", person.getName());
					jsonPerson.put("link", person.getUri());
					jsonPerson.put("email", person.getEmail());
					
					arrPerson.put(jsonPerson);
				}
			}
			
			JSONArray catLst = new JSONArray();
			
			for (SyndCategory category : entrie.getCategories())
			{
				catLst.put(category.getName());
			}
			
			jsonEntrie.put("categories", catLst);
			arr.put(jsonEntrie);
		}

		return (arr);
	}
	
	public LinkedList<String> extractCategories(SyndFeed feed)
	{
		LinkedList<String> lst = new LinkedList<String>();
		
		for (SyndEntry entrie : feed.getEntries())
		{
			for (SyndCategory category : entrie.getCategories())
			{
				if (lst.contains(category.getName()) == false)
					lst.add(category.getName());
			}
		}
		
		return lst;
	}
	
	@POST
	@Produces("application/json")
	public Response getSyndFeedForUrl(@QueryParam("remote") String url) throws MalformedURLException, IOException, IllegalArgumentException, FeedException {
		SyndFeed feed = null;		
		InputStream is = null;
		JSONObject jsonObject = new JSONObject();

		try {
			URLConnection openConnection = new URL(url).openConnection();	
			openConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			is = new URL(url).openConnection().getInputStream();
			if("gzip".equals(openConnection.getContentEncoding())) {
				is = new GZIPInputStream(is);
			}			
			InputSource source = new InputSource(is);			
			SyndFeedInput input = new SyndFeedInput();
			feed = input.build(source);
			
			return Response.status(200).entity(toJSON(feed).toString()).build();
		} catch (Exception e){
			jsonObject.put("error", "Invalid URL: " + url);
			jsonObject.put("exception", e.toString());
			return Response.status(404).entity(jsonObject.toString()).build();		
		} finally {
			if( is != null)	is.close();
		}

		/* return Response.status(201).build(); */
	}
}
