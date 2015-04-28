package server.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    // @RequestMapping(value = "/media/{contentName}", method = RequestMethod.GET)
    // public
    @RequestMapping(value = "/media/{name}", method = RequestMethod.GET)
    public HttpEntity<byte[]> getDocument(@PathVariable String name) {
       // send it back to the client
       HttpHeaders httpHeaders = new HttpHeaders();
       //httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       //httpHeaders.setContentType(MediaType.IMAGE_JPEG);
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
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
}
