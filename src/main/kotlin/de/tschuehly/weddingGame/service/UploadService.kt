package de.tschuehly.weddingGame.service

import com.github.sardine.SardineFactory
import com.github.sardine.impl.SardineException
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class UploadService(
) {
    @Value("\${WEBDAV_PW}")
    lateinit var webdavPw: String

    val channel = Channel<Pair<String,ByteArray>>(10000)

    var processedImages = 0

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @PostConstruct
    fun createWorkerGroup(){
        coroutineScope.launch{
            for(x in 1..5){
                launch {
                    println("Create Worker $x")
                    while (true){
                            uploadImage(channel.receive() )
                    }
                }
            }
        }
    }


    private suspend fun uploadImage(urlAndImage: Pair<String, ByteArray>){

        val (url,image) = urlAndImage
        println("Uploading Image: $url")
        val sardine = SardineFactory.begin("u292326-sub2",webdavPw)
        for (i in 0..5){
            try{
                sardine.put(url, image)
                processedImages += 1
                println("${processedImages}: Uploaded $url")
                break
            }catch (e: SardineException){
                if(e.statusCode == 408){
                    println(e)
                }
            }
        }
    }


    @PreDestroy
    fun cancelScope(){
        coroutineScope.cancel()
    }
}
