package com.serverless.utils

import com.amazonaws.AmazonClientException
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.transfer.TransferManager
import com.amazonaws.services.s3.transfer.TransferManagerBuilder

import java.io.File

/*
    Sample s3 upload and download methods using transfer manager
    You must define everything in <> to run this successfully
 */
class S3Operations {
  private val s3client: AmazonS3
  private val tx: TransferManager
  private val credentials: BasicAWSCredentials = BasicAWSCredentials(
     "<YOUR AWS_ACCESS_KEY_ID>",
     "<YOUR AWS_SECRET_ACCESS_KEY>")
  private val inputBucket =  "<your input bucket name>"
  private val outputBucket =  "<your output bucket name>"


  init {
    //Initialize both the s3 client and transfer manager  
    s3client =  AmazonS3ClientBuilder.standard()
                .withCredentials(AWSStaticCredentialsProvider(credentials))
//                .withRegion("eu-west-1") # This is optional unless you are working with multiple regions
                .build()
    tx = TransferManagerBuilder.standard().withS3Client(s3client).build()

  }

  /**
     * Downloads the file from the specified bucket with transfer manager
     *
     * @param keyName
     *            The s3 key of the file to be downloaded
     * @param file
     *            The file to download the object's data to.
     *
     * @return A Boolean indicating success/failure
     *
     */
  fun download(keyName: String, file: File): Boolean {
    val fileName = file.name
    val transfer = tx.download(inputBucket, keyName, file)

    return try {
      val ex: AmazonClientException? = transfer.waitForException()
      if (ex == null) {
        val msg =  "File $fileName has been downloaded."
        println(msg)
        true
      } else {
        false
      }
    } catch (ex: InterruptedException) {
      println(ex)
      false
    }
  }

   /**
    * Uploads the converted files to the output bucket with transfer manager
    *
    * @param directory
    *            The directory with the converted files
    * @param s3Directory
    *            The desired output directory on s3
    *
    * @return A Boolean indicating success/failure
    *
    */
  fun upload(directory: String, s3Directory: String): Boolean {

    val transfer = tx.uploadDirectory(outputBucket,s3Directory,File(directory),false)

    return try {
      val ex: AmazonClientException? = transfer.waitForException()
      tx.shutdownNow()
      if (ex == null) {
        val msg =  "Files have been uploaded."
        println(msg)
        true
      } else {
        false
      }
    } catch (ex: InterruptedException) {
      println(ex)
      false
    }
  }

}