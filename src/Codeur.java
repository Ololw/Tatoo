import java.io.File;
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
			for(int j=0 ; j < 4 ; j++)
			{
				//Ouais
			}
		}
	}
	
	public void codage() throws IOException
	{
		Path path = Paths.get(nomFichierPlanque);
	    dataPlanque = Files.readAllBytes(path);
		
		Path path2 = Paths.get(nomFichierCache);
		dataGangster = Files.readAllBytes(path2);
		
		
		
	}
}
