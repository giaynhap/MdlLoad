import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import javax.print.attribute.standard.Sides;

import com.jogamp.opengl.GL2;

public class Mdlheader {
	
	public static final int MAXSTUDIOTRIANGLES	=	20000;
	public static final int MAXSTUDIOVERTS		=	2048;
	public static final int MAXSTUDIOSEQUENCES	=	256;	 
	public static final int MAXSTUDIOSKINS		=	100 ;
	public static final int MAXSTUDIOSRCBONES	=	512;	
	public static final int MAXSTUDIOBONES		=	128;	
	public static final int MAXSTUDIOMODELS		=	32;
	public static final int MAXSTUDIOBODYPARTS	=	32;
	public static final int MAXSTUDIOGROUPS		=	4;
	public static final int MAXSTUDIOANIMATIONS	=	512;
	public static final int MAXSTUDIOMESHES		=	256;
	public static final int MAXSTUDIOEVENTS		=	1024;
	public static final int MAXSTUDIOPIVOTS		=	256;
	public static final int MAXSTUDIOCONTROLLERS=	8;
	
	
	public static final int STUDIO_HAS_NORMALS	=0x0001;
	public static final int STUDIO_HAS_VERTICES =0x0002;
	public static final int STUDIO_HAS_BBOX		=0x0004;
	public static final int STUDIO_HAS_CHROME	=0x0008; 
	
	public static final int STUDIO_NF_FLATSHADE		=0x0001;
	public static final int STUDIO_NF_CHROME		=0x0002;
	public static final int STUDIO_NF_FULLBRIGHT	=0x0004;
	public static final int STUDIO_NF_ADDITIVE		=32 ;
	public static final int STUDIO_NF_ALPHATEST		=64 ;
	public static final int STUDIO_NF_ADDCHROME		=34 ;
	public static final int STUDIO_NF_NOMIPMAP		=256;
	
	public int					id;
	public int					version;

	public String				name;  
	public byte[]			    c_name = new byte[64];
	public int					length;

	public float[]				eyeposition = new float[3];	 
	public float[] 				min = new float[3];			 
	public float[]				max = new float[3];			

	public float[]				bbmin= new float[3];			 
	public float[]				bbmax= new float[3];			

	public int					flags;

	public int					numbones;		 
	public int					boneindex;

	public int					numbonecontrollers;		 
	public int					bonecontrollerindex;

	public int					numhitboxes;			 
	public int					hitboxindex;			
	
	public int					numseq;			 
	public int					seqindex;

	public int					numseqgroups;	 
	public int					seqgroupindex;

	public int					numtextures;		 
	public int					textureindex;
	public int					texturedataindex;

	public int					numskinref;			 
	public int					numskinfamilies;
	public int					skinindex;

	public int					numbodyparts;		
	public int					bodypartindex;

	public int					numattachments;		 
	public int					attachmentindex;

	public int					soundtable;
	public int					soundindex;
	public int					soundgroups;
	public int					soundgroupindex;

	public int					numtransitions;		 
	public int					transitionindex;
 

	private byte[] mdl_data;
	public Mdlheader( byte[]  mdl_data)
	{
		this.mdl_data = mdl_data;
		byteBuffer = ByteBuffer.wrap(this.mdl_data);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		 
	}
	
	int little2big(int i) {
	    return (i&0xff)<<24 | (i&0xff00)<<8 | (i&0xff0000)>>8 | (i>>24)&0xff;
	}
	
	ByteBuffer byteBuffer;
	 

	
	public void parseHeader()
	{
		this.id = byteBuffer.getInt();
		this.version = byteBuffer.getInt();
		 
		 byteBuffer.get(this.c_name);
		 try {
			this.name = new String(this.c_name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.name = "n/a";
		} 
		 
		this.length =   byteBuffer.getInt();
		this.eyeposition[0] = byteBuffer.getFloat();
		this.eyeposition[1] = byteBuffer.getFloat();
		this.eyeposition[2] = byteBuffer.getFloat();
		this.min[0] = byteBuffer.getFloat();
		this.min[1] = byteBuffer.getFloat();
		this.min[2] = byteBuffer.getFloat();
		this.max[0] = byteBuffer.getFloat();
		this.max[1] = byteBuffer.getFloat();
		this.max[2] = byteBuffer.getFloat();
		this.bbmin[0] = byteBuffer.getFloat();
		this.bbmin[1] =byteBuffer. getFloat();
		this.bbmin[2] = byteBuffer.getFloat();
		this.bbmax[0] = byteBuffer.getFloat();
		this.bbmax[1] = byteBuffer.getFloat();
		this.bbmax[2] = byteBuffer.getFloat();
		this.flags =  byteBuffer.getInt();
		this.numbones =  byteBuffer.getInt();
		this.boneindex = byteBuffer.getInt();
		this.numbonecontrollers= byteBuffer.getInt();
		this.bonecontrollerindex= byteBuffer.getInt();
		this.numhitboxes= byteBuffer.getInt();
		this.hitboxindex= byteBuffer.getInt();
		this.numseq= byteBuffer.getInt();
		this.seqindex= byteBuffer.getInt();
		this.numseqgroups= byteBuffer.getInt();
		this.seqgroupindex= byteBuffer.getInt();
		this.numtextures= byteBuffer.getInt();
		this.textureindex=byteBuffer.getInt();
		this.texturedataindex= byteBuffer.getInt();
		this.numskinref= byteBuffer.getInt();
		this.numskinfamilies= byteBuffer.getInt();
		this.skinindex= byteBuffer.getInt();
		this.numbodyparts= byteBuffer.getInt();
		this.bodypartindex= byteBuffer.getInt();
		this.numattachments= byteBuffer.getInt();
		this.attachmentindex= byteBuffer.getInt();
		this.soundtable= byteBuffer.getInt();
		this.soundindex= byteBuffer.getInt();
		this.soundgroups= byteBuffer.getInt();
		this.soundgroupindex= byteBuffer.getInt();
		this.numtransitions= byteBuffer.getInt();
		this.transitionindex= byteBuffer.getInt();
	}
	textures[]	m_Textures ;
	seqdesc[]	m_Seq ;
	bone [] 	m_Bone;
	bodyparts[] m_Bodypart;
	seqgroup []m_SeqGoups;
	
	public void getBodypart()
	{
		m_Bodypart = new bodyparts[this.numbodyparts];
		
		ByteBuffer pBodyPart    = ByteBuffer.wrap(mdl_data,  this.bodypartindex, 76*this.numbodyparts);
		pBodyPart.order(ByteOrder.LITTLE_ENDIAN);
		for (int k=0;k<this.numbodyparts;k++)
		{
			bodyparts bodyparts = new bodyparts();
			
			pBodyPart.get(bodyparts.c_name);
			 try {
				 bodyparts.name = new String(bodyparts.c_name, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				bodyparts.name = "n/a";
			} 
			 bodyparts.nummodels = pBodyPart.getInt();
			 
			 
			 bodyparts.base = pBodyPart.getInt();
			 bodyparts.modelindex = pBodyPart.getInt();
			 m_Bodypart[k]= bodyparts;
			
		}
	}
	public void getModel()
	{
		//int index = m_bodynum /bodyparts.base;
		//index = index % bodyparts.nummodels;
	 
		getBodypart();
		for (int k=0;k<this.numbodyparts;k++)
		{
			bodyparts  bodyparts = m_Bodypart[k];
			 
			isanim = k;
			ByteBuffer pModel   = ByteBuffer.wrap(mdl_data,  bodyparts.modelindex, 112*bodyparts.nummodels);
			pModel.order(ByteOrder.LITTLE_ENDIAN);
			 bodyparts.model =new model[bodyparts.nummodels];
			for (int i=0;i<bodyparts.nummodels;i++)
			{
				model model = new model();
				pModel.get(model.c_name);
				 try {
					 model.name = new String(model.c_name, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					model.name = "n/a";
				} 
				 model.type = pModel.getInt(); 
				
				 
				 model.boundingradius = pModel.getInt(); 
				 model.nummesh = pModel.getInt(); 
				 model.meshindex = pModel.getInt();
				 model.numverts = pModel.getInt();
				 model.vertinfoindex = pModel.getInt();
				 model.vertindex = pModel.getInt();
				 model.numnorms = pModel.getInt();
				 model.norminfoindex = pModel.getInt();
				 model.normindex = pModel.getInt( );
				 model.numgroups = pModel.getInt( );
				 model.groupindex = pModel.getInt( );
				 bodyparts.model[i] = model;
				 
				 getMeshInfo(model);
				 getVertInfo(model);
			}
			
			
		}
		
	}
	
	private void getMeshInfo(model model)
	{
		ByteBuffer pMesh   = ByteBuffer.wrap(mdl_data,  model.meshindex, 20*model.nummesh);
		pMesh.order(ByteOrder.LITTLE_ENDIAN);
		model.m_Mesh = new  mesh[model.nummesh];
		for (int k=0;k<model.nummesh;k++)
		{
			
			mesh mesh = new mesh();
			mesh.numtris = pMesh.getInt();
			mesh.triindex = pMesh.getInt();
			mesh.skinref = pMesh.getInt();
			mesh.numnorms = pMesh.getInt();
			mesh.normindex = pMesh.getInt();
			
			
			
			
			ByteBuffer pTris   = ByteBuffer.wrap(mdl_data,  mesh.triindex,mdl_data.length- mesh.triindex-1);
			mesh.tris = new int[50*mesh.numtris];
			pTris.order(ByteOrder.LITTLE_ENDIAN);
			int ntri =0;
			int index = 0;
			//while(ntri < mesh.numtris)
			//{
			 
				
			for (int i=0;i<= mesh.numtris;i++)
			{
			 
				mesh.tris[ntri] = pTris.getShort();
				
				if (Math.abs(mesh.tris[ntri])>4000) break;
				 
				for (int a=0;a<Math.abs(mesh.tris[ntri]);a++,ntri+=4)
				{
					mesh.tris[ntri+1] = pTris.getShort();
					mesh.tris[ntri+2] = pTris.getShort();
					mesh.tris[ntri+3] = pTris.getShort();
					mesh.tris[ntri+4] = pTris.getShort();
				}
				ntri++;
				 
			}
			
			model.m_Mesh[k] = mesh;
			 
		}
		
	}
	private void getVertInfo(model model)
	{
		
		model.m_Normbone = new byte[model.numnorms];
		model.m_Vertbone = new byte[model.numverts];
		ByteBuffer pVertBone   = ByteBuffer.wrap(mdl_data,  model.vertinfoindex, model.numverts);
		pVertBone.order(ByteOrder.LITTLE_ENDIAN);
		pVertBone.get(model.m_Vertbone);
		
		ByteBuffer pNorsBone   = ByteBuffer.wrap(mdl_data,  model.norminfoindex, model.numnorms);
		pNorsBone.order(ByteOrder.LITTLE_ENDIAN);
		pNorsBone.get(model.m_Normbone);
		
		ByteBuffer pVert   = ByteBuffer.wrap(mdl_data,  model.vertindex, 12*model.numverts);
		ByteBuffer pNors   = ByteBuffer.wrap(mdl_data,  model.normindex,12* model.numnorms);
		
		pVert.order(ByteOrder.LITTLE_ENDIAN);
		pNors.order(ByteOrder.LITTLE_ENDIAN);
		model.m_Verts = new float[model.numverts*3];
		model.m_Norms = new float[model.numnorms*3];
		
	
		for (int k=0;k<model.numverts;k++)
		{
			
			model.m_Verts[k*3]=pVert.getFloat();
			model.m_Verts[k*3+1]=pVert.getFloat();
			model.m_Verts[k*3+2]=pVert.getFloat();
		
		}
		
		for (int k=0;k<model.numnorms;k++)
		{
			 
			
			model.m_Norms[k*3]=pNors.getFloat();
			model.m_Norms[k*3+1]=pNors.getFloat();
			model.m_Norms[k*3+2]=pNors.getFloat();
		 
		}
	}
	
	
	public void getBone()
	{
	
		m_Bone = new bone[this.numbones];
		ByteBuffer	pBone = ByteBuffer.wrap(mdl_data,  this.boneindex, 112*this.numbones);
		pBone.order(ByteOrder.LITTLE_ENDIAN);
		for (int k=0;k<this.numbones;k++)
		{
			bone bone = new bone();
			pBone.get(bone.c_name);
			 try {
				 bone.name = new String(bone.c_name, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					bone.name = "n/a";
				} 
			
			 bone.parent = pBone.getInt(); 
			 bone.flags = pBone.getInt(); 
			 bone.bonecontroller[0] = pBone.getInt();
			 bone.bonecontroller[1] = pBone.getInt(); 
			 bone.bonecontroller[2] = pBone.getInt(); 
			 bone.bonecontroller[3] = pBone.getInt(); 
			 bone.bonecontroller[4] = pBone.getInt(); 
			 bone.bonecontroller[5] = pBone.getInt(); 
			 
			 bone.value[0] = pBone.getFloat();
			 bone.value[1] = pBone.getFloat(); 
			 bone.value[2] = pBone.getFloat(); 
			 bone.value[3] = pBone.getFloat(); 
			 bone.value[4] = pBone.getFloat(); 
			 bone.value[5] = pBone.getFloat(); 
			 
			 bone.scale[0] = pBone.getFloat();
			 bone.scale[1] = pBone.getFloat(); 
			 bone.scale[2] = pBone.getFloat(); 
			 bone.scale[3] = pBone.getFloat(); 
			 bone.scale[4] = pBone.getFloat(); 
			 bone.scale[5] = pBone.getFloat(); 
			 
				 
			 
			 m_Bone[k] = bone;
			
		}
		
	}
	
	public void loadSeqGroup()
	{
		ByteBuffer	pSeqGroup = ByteBuffer.wrap(mdl_data,  this.seqgroupindex, 104*this.numseqgroups);
		pSeqGroup.order(ByteOrder.LITTLE_ENDIAN);
		m_SeqGoups = new seqgroup[this.numseqgroups];
		for (int k=0;k<this.numseqgroups;k++)
		{
			
		
			seqgroup seqgroup = new seqgroup();
			
			pSeqGroup.get(seqgroup.c_label);
			 try {
				 seqgroup.lable = new String(seqgroup.c_label, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					seqgroup.lable = "n/a";
				} 
		
			pSeqGroup.get(seqgroup.c_name);
			 try {
				 seqgroup.name = new String(seqgroup.c_name, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					seqgroup.name = "n/a";
				} 
			 seqgroup.cache = pSeqGroup.getInt();
			 seqgroup.data = pSeqGroup.getInt();
			 m_SeqGoups[k]= seqgroup;
		}
		
	}
	public seqhdr[] getSeqGroups()
	{
		seqhdr [] m_SeqGroup = new seqhdr[this.numseqgroups];
		ByteBuffer	pSeqGroup = ByteBuffer.wrap(mdl_data,  this.seqgroupindex, 76*this.numseqgroups);
		pSeqGroup.order(ByteOrder.LITTLE_ENDIAN);
		for (int k =0;k<this.numseqgroups;k++)
		{
		seqhdr seq = new seqhdr();
		
		
		 
			
			seq.id = little2big(pSeqGroup.getInt());
			seq.version = little2big(pSeqGroup.getInt());
			pSeqGroup.get(seq.c_name);
			 try {
				 seq.name = new String(seq.c_name, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					seq.name = "n/a";
				} 
			
			seq.length = little2big(pSeqGroup.getInt());
			m_SeqGroup[k] = seq;
		}
		 return m_SeqGroup;
	}
	int isanim=0;
	public void getSeq()
	{
		
		m_Seq   = new seqdesc[this.numseq];
		ByteBuffer	pSeq = ByteBuffer.wrap(mdl_data, this.seqindex, 177*this.numseq);
		pSeq.order(ByteOrder.LITTLE_ENDIAN);
		for (int k =0 ;k < this.numseq;k++)
		{
		seqdesc seq = new seqdesc();
		pSeq.get(seq.c_label);
		 try {
			 seq.lable = new String(seq.c_label, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				 seq.lable= "n/a";
			} 
		 seq.fps = pSeq.getFloat();
		 seq.flags = pSeq.getInt();
		 seq.activity = pSeq.getInt();
		 seq.actweight =pSeq. getInt();
		 seq.numevents = pSeq.getInt();
		 seq.eventindex = pSeq.getInt();
		 seq.numframes= pSeq.getInt();
		 seq.numpivots= pSeq.getInt();
		 seq.pivotindex= pSeq.getInt();
		 seq.motiontype= pSeq.getInt();
		 seq.motionbone= pSeq.getInt();
		 seq.linearmovement[0] = pSeq.getFloat();
		 seq.linearmovement[1] = pSeq.getFloat();
		 seq.linearmovement[2] = pSeq.getFloat();
		 seq.automoveposindex=pSeq.getInt();
		 seq.automoveangleindex=pSeq.getInt();
		 seq.bbmin[0]= pSeq.getFloat();
		 seq.bbmin[1]= pSeq.getFloat();
		 seq.bbmin[2]= pSeq.getFloat();
		 seq.bbmax[0]= pSeq.getFloat();
		 seq.bbmax[1]= pSeq.getFloat();
		 seq.bbmax[2]= pSeq.getFloat();
		 seq.numblends =  pSeq.getInt();
		 seq.animindex =  pSeq.getInt();
		 seq.blendtype[0] =  pSeq.getInt();
		 seq.blendtype[1] =  pSeq.getInt();
		 
		 seq.blendstart[0] =  pSeq.getFloat();
		 seq.blendstart[1] =  pSeq.getFloat();
		 seq.blendend[0] =  pSeq.getFloat();
		 seq.blendend[1] =  pSeq.getFloat();
		 
		 seq.blendparent =  pSeq.getInt();
		 seq.seqgroup =  pSeq.getInt();
		 seq.entrynode =  pSeq.getInt();
		 seq.exitnode =  pSeq.getInt();
		 seq.nodeflags =  pSeq.getInt();
		 seq.nextseq =  pSeq.getInt();
		 m_Seq[k] = seq;
		 
		 loadSeq(seq);
		 
		 
		}
		
		 
	}
	
	 public void loadSeq(seqdesc seqdesc)
	 {
		 ByteBuffer	pSeq  = null;
			
			for (int k=0;k<seqdesc.numframes;k++)
			{
				ArrayList mmBone = new ArrayList<float[]>();
				for (int i=0;i<this.numbones;i++) mmBone.add(new float[6]);
				seqdesc.pos.add(mmBone);
				
			 
			}
		 seqdesc.anim  = new anim[this.numbones];
		 	pSeq = ByteBuffer.wrap(mdl_data, seqdesc.animindex+m_SeqGoups[0].data, 12*this.numbones);
			pSeq.order(ByteOrder.LITTLE_ENDIAN);
			for (int m =0;m<seqdesc.numblends;m++ )
			{
			 if (seqdesc.seqgroup==0)
			 { 
				 for (int i=0;i<this.numbones;i++)
				 {
				 	anim anim = new anim();
				 	int current_pos = seqdesc.animindex+m_SeqGoups[0].data+ 12*seqdesc.numblends*i;
					pSeq = ByteBuffer.wrap(mdl_data, current_pos, 12*this.numbones);
					pSeq.order(ByteOrder.LITTLE_ENDIAN);
					anim.offset[0]= pSeq.getShort();
					anim.offset[1]=pSeq.getShort();
					anim.offset[2]=pSeq.getShort();
					anim.offset[3]=pSeq.getShort();
					anim.offset[4]=pSeq.getShort();
					anim.offset[5]=pSeq.getShort();
					seqdesc.anim [i] = anim;
				
							for (int k =0;k<seqdesc.numframes;k++)
						 getAnimValue(k,seqdesc,current_pos,anim,i);
						 
					 }
			 
			}
		}
			return ; 
		
		 
	 }
	
	 public void getAnimValue(int nframe,seqdesc seqdesc,int posdata,anim anim,int bone)
	 {	
		 	 
				
			 
			animvalue animvalue = new animvalue();
		
			for (int j = 0; j < 6; j++)
			{
				ByteBuffer	pValueAnim = null;
				try{
					pValueAnim  =  ByteBuffer.wrap(mdl_data, posdata+anim.offset[j], mdl_data.length-(posdata+anim.offset[j])-1);
					pValueAnim.order(ByteOrder.LITTLE_ENDIAN);
				} catch( IndexOutOfBoundsException e)
				{
					return ;
				}
				if (anim.offset[j]!=0)
				{
				
				
					
						 int valid = unsignedToBytes(pValueAnim.get());
						 int total =  unsignedToBytes(pValueAnim.get());
						 
						 int []value1 =new int[total+1];
						 for (int m=1;m<total+1;m++)
						 {
							 
							 value1[m] =  pValueAnim.getShort() ;
						 }
						
						 int k = nframe;
						 int addpos =0;
						while (k>=total)
						{ 
							
							k-=total;
							addpos+=(valid+1)*2;
							pValueAnim  =  ByteBuffer.wrap(mdl_data, posdata+anim.offset[j]+addpos, mdl_data.length-(posdata+anim.offset[j])-1-addpos);
							pValueAnim.order(ByteOrder.LITTLE_ENDIAN);
							valid = Byte.toUnsignedInt(pValueAnim.get());
							total =  unsignedToBytes(pValueAnim.get());
							value1 =new int[total+1];
							for (int m=1;m<total+1;m++)
							{
								 
								value1[m] =  pValueAnim.getShort() ;
							}  
						}

					 if ( valid > k)
						{
						
						 seqdesc.pos.get(nframe).get(bone)[j]  = m_Bone[bone].value[j]+value1[k+1] * m_Bone[bone].scale[j];
						
						 if (nframe==4 && isanim==2 && j<3)
						 {
							
							 //System.out.println(k+" "+value1[k+1] * m_Bone[bone].scale[j]);
						 }
						}
					 else
					 {
						 
						 
						  seqdesc.pos.get(nframe).get(bone)[j]  = m_Bone[bone].value[j]+ value1[valid  ] * m_Bone[bone].scale[j];
					 }
			 
				}
				else
				{
					//if (nframe==9)
						
					//System.out.println(m_Bone[bone].name);
					 seqdesc.pos.get(nframe).get(bone)[j] = m_Bone[bone].value[j];
				}
				 
				
			}
		 
			 
		 
	 }
	public void getTextures()
	{  
		 m_Textures   = new textures[this.numtextures];
		ByteBuffer	pTextures = ByteBuffer.wrap(mdl_data, textureindex, 80*this.numtextures);
		pTextures.order(ByteOrder.LITTLE_ENDIAN);
		
		for (int i=0;i<this.numtextures;i++)
		{
			 m_Textures[i] = new textures();
			pTextures.get(m_Textures[i].c_name);
			 try {
				 m_Textures[i].name = new String(m_Textures[i].c_name, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					m_Textures[i].name = "n/a";
				} 
			
			 m_Textures[i].flags = pTextures.getInt();
			 m_Textures[i].width = pTextures.getInt();
			 m_Textures[i].height= pTextures.getInt();
			 m_Textures[i].index = pTextures.getInt();
		}
	
	}
	public void loadTextures(GL2 gl)
	{
		
		for (int i=0;i<this.numtextures;i++)
		{
			int width = m_Textures[i].width;
			int height = m_Textures[i].height;
		
			byte [] data  = new byte[width*height];
			int [] data_int = new int[width*height];
			ByteBuffer	pTexData = ByteBuffer.wrap(mdl_data, m_Textures[i].index, width*height);
			pTexData.order(ByteOrder.LITTLE_ENDIAN);
			pTexData.get(data);
			for (int v =0 ;v <  width*height;v++)
			{ 
				data_int[v] =  unsignedToBytes(data[v]);
				 
			}
			int [] pal  = new int[width*height];
 
			for (int v=0;v<width*height&&v+m_Textures[i].index+width*height<mdl_data.length;v++)
			{
				pal[v] = unsignedToBytes(mdl_data[v+m_Textures[i].index+width*height]);
				
			}
	 
		 
			
	 
			UploadTexture(  gl,m_Textures[i],pal,data_int);
			
		}
	}
	public static int unsignedToBytes(byte b) {
	    return b & 0xFF;
	  }
 public void  UploadTexture(GL2 gl,textures ptexture,int[] pal,int []  data )
{
	
	int		i, j;
	int		[]row1 = new int [256], row2 = new int [256], col1 = new int [256], col2 = new int [256];
	int	 pix1,pix1_1,pix1_2, pix2,pix2_1,pix2_2, pix3,pix3_1,pix3_2, pix4,pix4_1,pix4_2;
	byte	[]tex, out;

	 
	int outwidth;
	for (outwidth = 1; outwidth < ptexture.width; outwidth <<= 1)
	{
		
	}

	 
	if (outwidth > 256)
		outwidth = 256;

	int outheight;
	for (outheight = 1; outheight < ptexture.height; outheight <<= 1)
		;

 
	if (outheight > 256)
		outheight = 256;

	tex = out = new byte[ outwidth * outheight * 4];
	 
 

	for (i = 0; i < outwidth; i++)
	{
		col1[i] = (int) ((i + 0.25) * (ptexture.width / (float)outwidth));
		col2[i] = (int) ((i + 0.75) * (ptexture.width / (float)outwidth));
	}

	for (i = 0; i < outheight; i++)
	{
		row1[i] = (int) ((i + 0.25) * (ptexture.height / (float)outheight)) * ptexture.width;
		row2[i] = (int) ((i + 0.75) * (ptexture.height / (float)outheight)) * ptexture.width;
	}

	int out_index =0;
	for (i=0 ; i<outheight ; i++)
	{
		for (j=0 ; j<outwidth ; j++, out_index += 4)
		{
			
			pix1 = pal[data[row1[i] + col1[j]] * 3];
		 
			pix1_1 = pal[data[row1[i] + col1[j]] * 3+1];
			pix1_2 = pal[(data[row1[i] + col1[j]]) * 3+2];
			
			pix2 = pal[(data [  row1[i] + col2[j] ]) * 3];
			pix2_1 = pal[(data[row1[i] + col2[j]] )* 3+1];
			pix2_2 = pal[(data[row1[i] + col2[j]]) * 3+2];
			
			pix3 = pal[(data[row2[i] + col1[j]] )* 3];
			pix3_1 = pal[(data[row2[i] + col1[j]] )* 3+1];
			pix3_2 = pal[(data[row2[i] + col1[j]]) * 3+2];
			
			pix4 = pal[(data[row2[i] + col2[j]] )* 3];
			pix4_1 = pal[(data[row2[i] + col2[j]]) * 3+1];
			pix4_2 = pal[(data[row2[i] + col2[j]]) * 3+2];
			out[out_index+0] = (byte) ((pix1  + pix2 + pix3  + pix4)>>2);
			out[out_index+1] = (byte) ((pix1_1 + pix2_1+ pix3_1 +pix4_1)>>2);
			out[out_index+2] = (byte) ((pix1_2 + pix2_2 +pix3_2+pix4_2)>>2);
			out[out_index+3] = (byte) 0xFF;
		}
	}
	gl.glEnable(GL2.GL_TEXTURE_2D);
	gl.glBindTexture( GL2.GL_TEXTURE_2D, ptexture.index ); //g_texnum );		
	gl.glTexImage2D( GL2.GL_TEXTURE_2D, 0, 3 , outwidth, outheight, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(out)  );
	gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
	gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER,  GL2.GL_LINEAR );
	gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR );

	 
}

 	public class seqgroup 
 	{
 		public  byte[]				c_label = new byte[32];
		public String 				lable ;
 		public  byte[]				c_name = new byte[64];
		public String 				name;
		public int					cache;		 
		public int					data;	 
 	}
	public class seqhdr
	{
		public 	int					id;
		public  int					version;

		public  byte[]				c_name = new byte[64];
		public String 				name;
		public  int					length;
	} 

	 
	public class bone
	{
		public  byte[]				c_name = new byte[32];
		public String				name;
		public int		 			parent;		 
		public int					flags;		 
		public int	[]				bonecontroller = new int[6];	 
		public float[]				value = new float[6];	 
		public float[]				scale = new float[6];    
	} 
	
	public class bonecontroller
	{
		public int					bone;	
		public int					type;	 
		public float				start;
		public float				end;
		public int					rest;	 
		public int					index; 
	}  

	public class seqdesc
	{
		public  byte[]				c_label = new byte[32];
		public String 				lable ;
		public float				fps;		 
		public int					flags;		 

		public int					activity;
		public int					actweight;

		public int					numevents;
		public int					eventindex;

		public int					numframes;	 

		public int					numpivots;	 
		public int					pivotindex;

		public int					motiontype;	
		public int					motionbone;
		public float []				linearmovement = new float[3] ;
		public int					automoveposindex;
		public int					automoveangleindex;

		public float []				bbmin = new float[3] ;
		public float []				bbmax = new float[3] ;		

		public int					numblends;
		public int					animindex;		 
											 
		public int[]				blendtype = new int [2];	 
		public float[]				blendstart = new float[2]; 
		public float[]				blendend  = new float[2];	;	 
		public int					blendparent;

		public int					seqgroup;		 

		public int					entrynode;		 
		public int					exitnode;		 
		public int					nodeflags;		 
		
		public int					nextseq;	
		public anim[]					anim;
		public ArrayList <ArrayList<float[]>>pos = new ArrayList< ArrayList<float[]>>();
	}  
 
	public class textures {
		public byte[]					c_name = new byte[64];
		public String 					name;
		public int						flags;
		public int						width;
		public int						height;
		public int						index;
	 
	}
	public class model
	{
		
		public byte[]  				c_name= new byte[64];
		public String 				name;
		public int					type;

		public float				boundingradius;

		public int					nummesh;
		public int					meshindex;

		public int					numverts;		
		public int					vertinfoindex;	
		public int					vertindex;	
		public int					numnorms;		
		public int					norminfoindex;
		public int					normindex;	

		public int					numgroups;	
		public int					groupindex;
		
		public mesh[] 				m_Mesh;
		public byte[]				m_Vertbone;
		public byte[]				m_Normbone;
		public float[]				m_Verts;
		public float[]				m_Norms;
		public short[]				m_Skinref;
	
		
	}
 
	public class mesh 
	{
		public int					numtris;
		public int					triindex;
		public int					skinref;
		public int					numnorms;		  
		public int					normindex;		 
		public int []				tris;
	}  

	public class trivert 
	{
		public short				vertindex;		 
		public short				normindex;		 
		public short				s,t;			 
	} 
	
	public class bodyparts
	{
		public byte[]  				c_name = new byte[64];
		public String 				name;
		public int					nummodels;
		public int					base;
		public int					modelindex; 
		public model[]				model;
		
	} 
	public class animvalue
	{
		public byte	total;
		public byte	valid;
		public short		value;
		
	}
	public class anim
	{
		public short	offset[] = new short[6];
	}  


	
}
