import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Decodeur
{
	String nomF;
	List<byte[]> poidsFaible = new ArrayList<byte[]>();
	
	public Decodeur(String nomFichier)
	{
		nomF = nomFichier;
	}
	
	public byte createByte(byte[] bytes, int offset)
	{
		byte byte0 = bytes[offset];
		byte byte1 = bytes[offset + 1];
		byte byte2 = bytes[offset + 2];
		byte byte3 = bytes[offset + 3];
		
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
	
	public String recupPoidsFaible()
	{		
		try
		{
			Path path = Paths.get(nomF);
			byte[] bytes = Files.readAllBytes(path);
			byte florian = 12;
			
			int offset = 31;
			
			while(florian != 0)
			{
				florian = this.createByte(bytes, offset);
				System.out.println((char)florian);
				offset = offset + 4;
			}
			
			this.createByte(bytes, 31);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
				
		
		return null;
	}
}
