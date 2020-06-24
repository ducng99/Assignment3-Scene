package scene;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;

import utils.Utils;

public class Material {

	public static void ground(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
		
		float amb[] = { .0f, .05f, .0f, 1.0f };
		float diff[] = { 0.2f, 0.3f, 0.15f, 1.0f };
		float spec[] = { 0.2f, 0.35f, 0.25f, 1.0f };
		float shine = 20.0f;
		gl.glMaterialfv(GL2.GL_FRONT, GLLightingFunc.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GLLightingFunc.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GLLightingFunc.GL_SPECULAR, spec, 0);
		gl.glMaterialf(GL2.GL_FRONT, GLLightingFunc.GL_SHININESS, shine);
	}
	
	public static void road(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
		
		float amb[] = { 0.3f, 0.3f, 0.3f, 1f };
		float diff[] = { 0.5f, 0.5f, 0.5f, 1f };
		float spec[] = { .8f, .8f, .8f, 1f };
		float shine = 50.0f;
		gl.glMaterialfv(GL2.GL_FRONT, GLLightingFunc.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GLLightingFunc.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GLLightingFunc.GL_SPECULAR, spec, 0);
		gl.glMaterialf(GL2.GL_FRONT, GLLightingFunc.GL_SHININESS, shine);
	}
	
	public static void water(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
		
		float amb[] = { 0.2f, 0.2f, 0.2f, 0.7f };
		float diff[] = { 0.2f, 0.2f, 0.2f, 0.77f };
		float spec[] = { 1.f, 1.f, 1.f, .9f };
		float shine = 90.0f;
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_SPECULAR, spec, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_SHININESS, shine);
	}

	public static void glass(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
		
		float amb[] = { 0.7f, 0.7f, 0.7f, 0.1f };
		float diff[] = { .7f, .7f, .7f, 0.3f };
		float spec[] = { 0.8f, 0.8f, 0.8f, 1f };
		float shine = 76.8f;
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_SPECULAR, spec, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_SHININESS, shine);
	}

	public static void metal(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
		
		float[] amb = { 0.1f, 0.3f, 0.15f, 1f };
		float[] diff = { 0.7f, 0.5f, 0.6f, 1f };
		float[] specular = { 1f, 1f, 1f, 1f };
		float shine = 90.8f;
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specular, 0);
		gl.glMaterialf(GL2.GL_FRONT, GLLightingFunc.GL_SHININESS, shine);
	}

	public static void matte(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
		
		float[] amb = { 0.05f, 0.05f, 0.05f, 1.0f };
		float[] diff = { 0.1f, 0.1f, 0.1f, 1.0f };
		float[] specular = { 0.5f, 0.5f, 0.5f, 1.0f };
		float shine = 10.0f;
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specular, 0);
		gl.glMaterialf(GL2.GL_FRONT, GLLightingFunc.GL_SHININESS, shine);
	}
	
	public static void tree(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
		
		float[] amb = { 0.043f, 0.173f, 0.043f, 1.0f };
		float[] diff = { 0.133f, 0.545f, 0.133f, 1.0f };
		float[] specular = { 0.165f, 0.671f, 0.165f, 1.0f };
		float shine = 10.0f;
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specular, 0);
		gl.glMaterialf(GL2.GL_FRONT, GLLightingFunc.GL_SHININESS, shine);
	}
	
	public static void building(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
	
		// Generate random colours for buildings to make them less lame
		float[] amb = { Utils.genRand(0.1f, 0), Utils.genRand(0.1f, 0f), Utils.genRand(0.1f, 0f), 1.0f };
		float[] diff = { amb[0] + 0.05f, amb[1] + 0.05f, amb[2] + 0.05f, 1.0f };
		float[] specular = { amb[0] + 0.05f, amb[1] + 0.05f, amb[2] + 0.05f, 1.0f };
		float shine = 10.0f;
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specular, 0);
		gl.glMaterialf(GL2.GL_FRONT, GLLightingFunc.GL_SHININESS, shine);
	}
}
