package com.alivin.myblog.config;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Fer
 * date 2021/8/30
 */
@Component
public class QiNiuConfig {

    @Value("${qiniu.accessKey}")
    private String ACCESS_KEY;

    @Value("${qiniu.secretKey}")
    private String SECRET_KEY;

    // 仓库
    @Value("${qiniu.bucket}")
    private String BUCKET;

    // 外网访问地址
    @Value("${qiniu.path}")
    public String PATH;

    public String upload(MultipartFile file, String fileName){
        //构造一个带指定Zone对象的配置类  zone0 代表的是华东
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        try {
            String upToken = auth.uploadToken(BUCKET);
            Response response = null;
            response = uploadManager.put(file.getInputStream(), fileName, upToken, null, null);

            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
