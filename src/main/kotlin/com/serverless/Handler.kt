package com.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.S3Event
import com.serverless.utils.Converter
import com.serverless.utils.S3Operations
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.io.IOException
import java.net.URLDecoder


/*
  Handler handless the event from the s3 bucket defined in the serveless.yml,
  in this case the event is a create object event
 */
class Handler : RequestHandler<S3Event, String> {


  companion object {
    // Initialize our converter and s3 classes
    val s3 = S3Operations()
    val converter = Converter()

  }


  override fun handleRequest(s3event: S3Event, context: Context): String {
       return try {
            // Get the s3 key of the file that has just been created
            val s3ObjectKey = URLDecoder.decode(s3event.records[0].s3.`object`.key.replace('+', ' '), "UTF-8")
            println("Processing $s3ObjectKey")

            // Get the file name without the extension
            val output = FilenameUtils.removeExtension(s3ObjectKey)

            // Download file from s3 - file must be downloaded to tmp folder
            val downloadResult = s3.download(s3ObjectKey,File("/tmp/$s3ObjectKey"))

            // Convert it with the converter if download succeeded
            val converterResult = if (downloadResult) {
              converter.process("/tmp/$s3ObjectKey",output)
            }else{
              false
            }

            // Upload the output to output s3 Bucket if conversion succeeded
            val uploadResult = if (converterResult) {
             s3.upload("/tmp/$output",output)
            }else{
              false
            }

            // Throw an exception if something fails
            if(uploadResult) {
              "Success"
            }
            else {
              throw Exception("Something went wrong")
            }

        } catch (ex: IOException) {

            throw RuntimeException(ex)

       } catch (ex: InterruptedException) {
         println(ex)
         throw RuntimeException(ex)
       }
    }
}
