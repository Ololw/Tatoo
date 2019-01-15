import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Decodeur
{
	int taille;
	String nomF;
	String nomImageCachee;
	
	byte[] data = null;
	byte[] pixels;
	
	public Decodeur(String nomFichier)
	{
		nomF = nomFichier;
		recupNomTaille();
		pixels = new byte[this.taille];
	}
	
	public byte createByte(byte[] bytes, int offset)
	{	
		byte res = 0;
		int i = 0;
        byte bitmask = 0x03;

		
		while(i < 4)
		{
			res = (byte) (res << 2);
			res = (byte) (res + (bytes[offset+i] & bitmask));
			i++;
		}
		return res;
	}
	
	public int createByteInt(byte[] bytes, int offset)
	{
		byte byte1 = 0;
		byte byte2 = 0;
		byte byte3 = 0;
		byte byte4 = 0;
		int res = 0;
		int res2 = 0;
		int res3 = 0;
		int res4 = 0;

		
		byte1 = this.createByte(bytes, offset);
		byte2 = this.createByte(bytes, offset+4);
		byte3 = this.createByte(bytes, offset+8);
		byte4 = this.createByte(bytes, offset+12);

		
		res = (byte1 & 0xFF);
		res = res << 24;
				
		res2 = (byte2 & 0xFF);
		res2 = res2 << 16;
		res = res + res2;
				
		res3 =  (byte3 & 0xFF);
		res3 = res3 << 8;
		res = res + res3;
				
		res4 =  (byte4 & 0xFF);
		res = res + res4;
		
		return res;
	}
	
	
	public String recupNomTaille()
	{		
		try
		{
			Path path = Paths.get(nomF);
			data = Files.readAllBytes(path);
			byte florian = 12;
			
			int taille;
			
			//Récupération de la taille de l'image cachée
			int offsetTaille = 15;
			taille = this.createByteInt(data,  offsetTaille);
			this.taille = taille;
			
			//Récupération du nom de l'image cachée
			int offset = 31;
			String nomF = "";
			
			while(florian != 0)
			{
				florian = this.createByte(data, offset);
				if(florian != 0)
					nomF = nomF + (char)florian;
				offset = offset + 4;
			}
			this.nomImageCachee = nomF;
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		return null;
	}
	
	public void getData()
	{
		int offset = 159;
		int i;
		
		if(data != null)
		{
			for(i = 0; i < this.taille; i++)
			{
				pixels[i] = this.createByte(data, offset);
				offset += 4;
			}
		}

	}
}
