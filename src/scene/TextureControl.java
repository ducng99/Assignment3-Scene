package scene;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureControl {
	private static HashMap<String, Texture> textureMap = new HashMap<>();
	public static GL2 gl;
	
	/**
	 * Importing image files as {@link Texture} objects then put them in a {@link HashMap} with fixed name
	 */
	public static void importTextures() {
		try 
		{
			textureMap.put("Water", TextureIO.newTexture(new File("./img/water.jpg"), true));	// looks like mesh blue wallpaper but it's free
			textureMap.put("Moon", TextureIO.newTexture(new File("./img/moon.jpg"), true));
			textureMap.put("FlatBase", TextureIO.newTexture(new File("./img/ground.jpg"), true));
			textureMap.put("Sky", TextureIO.newTexture(new File("./img/sky.jpg"), true));
			
			textureMap.forEach((name, tex) -> {
				if (gl != null) tex.disable(gl);
			});
		}
		catch (IOException e) 
		{
			System.out.println("Cannot load texture images!");
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get the texture stored in the hashmap base on given name
	 * @param name name of texture
	 * @return a {@link Texture} object linked to given name
	 */
	public static Texture getTexture(String name)
	{
		if (textureMap.containsKey(name))
			return textureMap.get(name);
		
		System.out.println("Specified texture name not found!");
		return null;
	}
	
	/**
	 * Setup texture on given {@link GL2 gl} object with texture name
	 * @param gl
	 * @param name name of texture
	 */
	public static void setupTexture(GL2 gl, String name)
	{
		gl.glEnable(GL2.GL_TEXTURE_2D);
		Texture tex = getTexture(name);
		
		if (tex != null)
		{
			tex.enable(gl);
			tex.bind(gl);
			
			tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
			tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
			tex.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
		}
	}
	
	/**
	 * Disable texture with given name on {@link GL2 gl} object
	 * @param gl
	 * @param name name of texture
	 */
	public static void disableTexture(GL2 gl, String name)
	{
		Texture tex = getTexture(name);
		
		if (tex != null)
		{
			tex.disable(gl);
		}
	}
}
