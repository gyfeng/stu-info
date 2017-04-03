package com.codedoge.cqut.stu.collect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Code Doge(http://www.codedoge.com/)
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Component
@EnableScheduling
public class AutoRestartApplication {

    @Value("${GITHUB_USERNAME}")
    private String gitUserName;

    @Value("${GITHUB_PASSWORD}")
    private String gitPassword;

    @Value("${GITHUB_URL}")
    private String gitURL;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Shanghai")
    public void execute() throws IOException, GitAPIException {
        File gitDir = new File("/tmp/stu-info" + new Date().getTime());
        try (Git git = Git.cloneRepository().setDirectory(gitDir)
                .setURI(gitURL).call()) {
            File restartLastFile = new File(gitDir, "restart-last");
            try (FileOutputStream out = new FileOutputStream(restartLastFile);
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
                writer.write(String.valueOf(new Date().getTime()));
                writer.newLine();
                writer.flush();
            }
            git.add().addFilepattern(".").call();
            git.commit().setAuthor("Code Doge", "yuanfeng.guo@foxmail.com").setMessage("自动发布程序").call();
            git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitUserName, gitPassword)).call();
        }
    }

}
