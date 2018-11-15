import entities.Authentication;
import impl.HMACSignatureParser;
import impl.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Key;
import java.util.List;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SigntauteTestMain {
    public static void main(String[] args) throws IOException {

        /*
       token created using

       Header:
        {
  "alg": "HS256",
  "typ": "JWT"
}

and Payload :

      {
  "sub": "1334567890",
  "name": "John Doe",
  "iat": 1516239022
}
 */
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIx" +
                "MzM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIy" +
                "fQ.w93VOmFA48U_U4ClUH0ZOr21L34OjvZacsoahOouMuo";

        String secret = "##$%^78prPLsksk@###TTTTTTTTTTTTTTTTTTT%%%%ttttttttt";

        // the key formation is key here , Need to know the type of secret retrieved from the keystore/wallet
        // The assumption here is that secret key is "UTF-8" based .. Note: Key should not be UTF-16 based or
        // else this validation will fail.

        Properties prop = new Properties();
        ClassLoader classLoader = new SigntauteTestMain().getClass().getClassLoader();

        File file = new File(classLoader.getResource("app.properties").getFile());

        prop.load(new InputStreamReader(new FileInputStream(file)));
        Key k = new SecretKeySpec(DatatypeConverter.parseBase64Binary(DatatypeConverter.printBase64Binary(prop.getProperty
                ("app.secret").getBytes(UTF_8))),
                SignatureAlgorithm.HS256.getJcaName());

        HMACSignatureParser parser = new HMACSignatureParser(k);
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("manager1");
        EntityManager entityManager = null;

        try {
            for (int i = 1; i < 10; i++) {

                if (parser.isValidJWT(prop.getProperty("app.token"))) {

                    entityManager = factory.createEntityManager();

                    entityManager.getTransaction().begin();
                    entityManager.persist(new Authentication(true));
                    entityManager.getTransaction().commit();

                    List<Authentication> authenticationList = entityManager.createQuery("FROM Authentication").getResultList();

                    for (Authentication auth : authenticationList) {
                        System.out.println(auth.getId() + " " + auth.isStatus());
                    }
                }

            }
        } finally {
            if (entityManager != null) entityManager.close();
            factory.close();

        }
        //  System.out.println(parser.isValidJWT(prop.getProperty("app.token")));
    }
}
