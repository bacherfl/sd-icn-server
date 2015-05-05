package server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by florian on 28.04.15.
 */
@RestController
public class MediaFileController {

    @Value("${ip}")
    String myIp;

    @Value("${sdicnapp.location}")
    String appLocation;

    // @RequestMapping(value = "/media/{contentName}", method = RequestMethod.GET)
    // public
    @RequestMapping(value = "/media/{name}", method = RequestMethod.GET)
    public HttpEntity<byte[]> getDocument(@PathVariable String name) {
       // send it back to the client
        System.out.println("received request for " + name);
       HttpHeaders httpHeaders = new HttpHeaders();

        //return new ResponseEntity<>(test.getBytes(), httpHeaders, HttpStatus.OK);
       httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       httpHeaders.setContentType(MediaType.IMAGE_JPEG);

       if (Files.exists(Paths.get(name))) {
           File f = new File(name);
           try {
               BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
               return new ResponseEntity<>(IOUtils.readFully(bis, -1, true), httpHeaders, HttpStatus.OK);
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

       return null;

    }

    @RequestMapping(value="/upload", method= RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();

                MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

                params.add("contentLocation", myIp);
                params.add("contentName", "/" + name);
                RestTemplate template = new RestTemplate();

                template.postForObject("http://" + appLocation + "/location/add", params, String.class);

                //contentName, contentLocation, /location/add
                return "You successfully uploaded " + name + "!" + myIp;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
}
