package ait.cohor63.shop.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Sergey Bugaenko
 * {@code @date} 15.09.2025
 */

public interface FileService {

    String upload(MultipartFile file, String productFile);
}
