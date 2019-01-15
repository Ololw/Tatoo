import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Main
{

	public static void main(String[] args) throws IOException
	{
		
		//******************************************************************************************************
		//Partie codage
		Codeur codeur = new Codeur("tatoumina1.jpg", "Lena.pgm");
		codeur.codage();
		

		//Partie decodage 
		System.out.println("Decodage : ");
		Decodeur decodeur = new Decodeur("Encode/test.pgm");
		System.out.println("Nom de l'image cachee : " + decodeur.nomImageCachee + ", Taille : " + decodeur.taille);
		decodeur.getData();

		//Ecriture du fichier decode dans un autre fichier
		File dest = new File("Decode/"+decodeur.nomImageCachee);
		dest.createNewFile();
		ByteArrayInputStream sourceFile = new ByteArrayInputStream(decodeur.pixels);
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
