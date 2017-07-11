import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
 
public class Mdlload {

	
	public  static Mdlheader load( )
	{
		ByteBuffer byteBuffer; 
		String strFile  = "F:\\CF_MODEL\\PV-AK74.mdl";
		File f = new File(strFile);
		int fSize= (int) f.length();
		Mdlheader header = null ;
		if (!f.exists()) return null;
		byte [] mdl_data = new byte[fSize];
		try {
			FileInputStream  in = new FileInputStream(f);
			
			in.read(mdl_data);
			
			
			 header  = new Mdlheader(mdl_data);
			header.parseHeader();
			header.getBone();
			header.getTextures();
			header.loadSeqGroup();
			header.getSeq();
		
			header.getModel();
		//	for (int k=0;k< header.numtextures;k++)
		//	System.out.println(header.m_Textures[k].name + " width: "+ header.m_Textures[k].width+"   height: "+ header.m_Textures[k].height+"   flags: "+ header.m_Textures[k].flags);
		//	for (int k=0;k< header.numseq;k++)
		//		System.out.println(header.m_Seq[k].lable + " fps: "+header.m_Seq[k].fps+ "			 numframe: "+header.m_Seq[k].numframes);
			
		
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return header;
	}
	
	
	
}
