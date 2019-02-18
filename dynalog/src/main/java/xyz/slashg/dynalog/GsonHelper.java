package xyz.slashg.dynalog;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Helper class that holds a static instance of Gson with the standard {@link FieldNamingPolicy}
 * Any Gson related util methods must be added here.
 *
 * @author SlashG
 * @created 08/09/18
 * @since <nextVersion/>
 */
public class GsonHelper {
	private static Gson gson;

	private static void initGsonIfNeeded()
	{
		if(gson == null)
			gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	}
	public static Gson getGsonInstance(){
		initGsonIfNeeded();
		return gson;

	}
}
