package com.human.facade.file;

import com.human.facade.pojo.MinioPojo;
import com.human.common.http.ResponseDTO;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class FileApiController implements FileApi {

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioPojo minioPojo;

    @Override
    @PostMapping("/common/upload")
    public ResponseDTO upload(MultipartFile file) {
        try {
            //判断桶是否存在
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioPojo.getBucketName()).build());
            if (!bucketExists){
                //如果不存在，就创建桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioPojo.getBucketName()).build());
            }
            //本地时间，具体到年、月、日
            String yyyymmdd = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String filename = yyyymmdd+"/"+file.getOriginalFilename();
            //加一个/表示创建一个文件夹
            minioClient.putObject(PutObjectArgs.builder().
                    bucket(minioPojo.getBucketName()).
                    object(filename).
                    stream(file.getInputStream(), file.getSize(), -1).
        //文件上传的类型，如果不指定，那么每次访问时都要先下载文件
        contentType(file.getContentType()).
                    build());
            String url= minioPojo.getUrl()+"/"+ minioPojo.getBucketName()+"/"+filename;
            return ResponseDTO.ok(url);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败",e);
        }
    }
}
