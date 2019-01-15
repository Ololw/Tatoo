import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Codeur
{
	String nomFichierCache;
	String nomFichierPlanque;
	File fichierCache;

	long taille;
	byte[] dataPlanque;
	byte[] dataGangster;
	
	public Codeur(String nomFCache, String nomFPlanque)
	{
		this.nomFichierCache = nomFCache;
		this.nomFichierPlanque = nomFPlanque;
		fichierCache = new File(nomFCache);
		taille = fichierCache.length();
	}
	
	public void codageTaille()
	{
		byte bitmask = 0x03;
		long test = taille;
		int i;
		int offset = 30;
		byte res;
		
		for(i=0; i<=15 ; i++)
		{
			res = (byte) (test & bitmask);
			test = test/4;
			System.out.println(res);
			
			dataPlanque[offset-i] = (byte) (dataPlanque[offset - i] >> 2);
			dataPlanque[offset-i] = (byte) (dataPlanque[offset - i] << 2);
			dataPlanque[offset-i] = (byte) (dataPlanque[offset - i] + res);
			
		}	
	}
	
	public void codageNom()
	{
		byte bitmask = 0x03;
		String test = nomFichierCache;
		int i;
		int offset = 31;
		byte res;
		
		byte[] nom = nomFichierCache.getBytes();
		
		for(i=0; i< nom.length; i++)
		{
			for(int j=3 ; j >= 0 ; j--)
			{
				res = nom[i];
				res = (byte) (res << (3-j) * 2 );
				res = (byte) (res >> 6);
				
				dataPlanque[offset + 3 - j] = (byte) (dataPlanque[offset + 3 - j] >> 2);
				dataPlanque[offset + 3 - j] = (byte) (dataPlanque[offset + 3 - j] << 2);
				dataPlanque[offset + 3 - j] = (byte) (dataPlanque[offset + 3 - j] + res);
				
			}
			offset = offset + 4;
		}
		
		for(i = offset ; i < 159; i++)
		{
			dataPlanque[i] = (byte) (dataPlanque[i] >> 2);
			dataPlanque[i] = (byte) (dataPlanque[i] << 2);
		}
	}
	
	public void codageData()
	{
		byte res;
		int offset = 159;
		
		for(int i=0; i< taille; i++)
		{
			for(int j=3 ; j >= 0 ; j--)
			{
				res = dataGangster[i];
				res = (byte) (res << (3-j) * 2 );
				res = (byte) (res >> 6);
				
				dataPlanque[offset + 3 - j] = (byte) (dataPlanque[offset + 3 - j] >> 2);
				dataPlanque[offset + 3 - j] = (byte) (dataPlanque[offset + 3 - j] << 2);
				dataPlanque[offset + 3 - j] = (byte) (dataPlanque[offset + 3 - j] + res);
				
			}
			offset = offset + 4;
		}
	}
	
	public void codage() throws IOException
	{
		Path path = Paths.get(nomFichierPlanque);
	    dataPlanque = Files.readAllBytes(path);
		
		Path path2 = Paths.get(nomFichierCache);
		dataGangster = Files.readAllBytes(path2);
		
		this.codageTaille();
		this.codageNom();
		this.codageData();
		
		this.write("FichierSecretDuRenseignementFrancais.pgm");
		
	}
	
	public void write(String nomFichier) throws IOException
	{
		File dest = new File("Encode/"+nomFichier);
		dest.createNewFile();
		ByteArrayInputStream sourceFile = new ByteArrayInputStream(dataPlanque);
		try
		{
			FileOutputStream destinationFile = new FileOutputStream(dest);

			try
			{
				byte buffer[] = new byte[512 * 1024];
				int nbLecture;

				while ((nbLecture = sourceFile.read(buffer)) != -1)
				{
					destinationFile.write(buffer, 0, nbLecture);
				}
			} finally
			{
				destinationFile.close();
			}
		} finally
		{
			sourceFile.close();
		}
	}
	
}
