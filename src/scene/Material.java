package scene;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;

public class Material {

	public static void ground(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
		
		float amb[] = { .0f, .0f, .0f, 1.0f };
		float diff[] = { 0.64f, 0.8f, 0.55f, 1.0f };
		float spec[] = { 0.2f, 0.35f, 0.25f, 1.0f };
		float shine = 20.0f;
		gl.glMaterialfv(GL2.GL_FRONT, GLLightingFunc.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GLLightingFunc.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GLLightingFunc.GL_SPECULAR, spec, 0);
		gl.glMaterialf(GL2.GL_FRONT, GLLightingFunc.GL_SHININESS, shine);
	}
	
	public static void moon(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
		
		float amb[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float diff[] = { 1f, 1f, 1f, 1.0f };
		float spec[] = { 1f, 1f, 1f, 1.0f };
		float shine = 100.0f;
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_SPECULAR, spec, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_SHININESS, shine);
	}
	
	public static void water(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
		
		float amb[] = { 1.f, 1.f, 1.0f, 0.7f };
		float diff[] = { 0.2f, 0.2f, 0.2f, 0.75f };
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
		
		float amb[] = { 0.6f, 0.6f, 0.6f, 0.1f };
		float diff[] = { .6f, .6f, .6f, 0.7f };
		float spec[] = { 0.6f, 0.6f, 0.6f, 0.8f };
		float shine = 76.8f;
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_SPECULAR, spec, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GLLightingFunc.GL_SHININESS, shine);
	}

	public static void metal(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
		
		float[] amb = { 0.021f, 0.174f, 0.021f, 1f };
		float[] diff = { 0.075f, 0.614f, 0.075f, 1f };
		float[] specular = { 0.633f, 0.727f, 0.633f, 1f };
		float shine = 90.8f;
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specular, 0);
		gl.glMaterialf(GL2.GL_FRONT, GLLightingFunc.GL_SHININESS, shine);
	}

	public static void chrome(GL2 gl)
	{
		gl.glEnable(GL2.GL_LIGHTING);
		
		// Chrome
		float[] amb = { 0.05f, 0.05f, 0.05f, 1.0f };
		float[] diff = { 0.1f, 0.1f, 0.1f, 1.0f };
		float[] specular = { 0.5f, 0.5f, 0.5f, 1.0f };
		float shine = 10.0f;
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, amb, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diff, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specular, 0);
		gl.glMaterialf(GL2.GL_FRONT, GLLightingFunc.GL_SHININESS, shine);
	}
}
