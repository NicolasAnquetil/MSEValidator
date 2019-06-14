package fr.inria;

import ch.akuhn.fame.Repository;
import ch.akuhn.fame.parser.ParseError;
import eu.synectique.verveine.core.gen.famix.Entity;
import eu.synectique.verveine.core.gen.famix.FAMIXModel;

import java.io.*;
import java.util.Collection;

public class MSEValidator {

    public static void main(String[] args) {
        new MSEValidator().validate(args);
    }


    public void validate(String[] args) {
        int result = -1;
        for (String arg : args) {
            File f = openFile(arg);
            if (f != null) {
                try {
                    result = validate( new FileInputStream(f) );
                } catch (ParseError err) {
                    System.err.println("Error parsing MSE stream: "+err.getMessage());
                 } catch (FileNotFoundException err) {
                    System.err.println("Error opening file "+arg+": "+err.getMessage());
                } catch (IOException err) {
                    System.err.println("IO error reading MSE stream: "+err.getMessage());
                } catch (Exception err) {
                    System.err.println("Error reading MSE stream: "+err.getMessage());
                } catch (Error err) {
                    System.err.println("Error reading MSE stream: "+err.getMessage());
                }
            }
        }
        System.out.println("Found "+result+" entities");
    }

    public int validate(String src) throws Error, Exception {
        return validate( new ByteArrayInputStream(src.getBytes()));
    }

    private int validate(InputStream mseStream) throws Error, Exception {
        Repository repo = new Repository(FAMIXModel.metamodel());
        repo.importMSE(mseStream);
        mseStream.close();
        return repo.getElements().size();
    }

    private File openFile(String fname) {
        File f = new File(fname);
        if (f.exists()) {
            return f;
        }
        System.err.println("Could not find file "+fname);
        return null;
    }

}
