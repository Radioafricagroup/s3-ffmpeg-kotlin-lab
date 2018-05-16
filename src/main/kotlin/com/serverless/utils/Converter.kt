package com.serverless.utils

import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFmpegExecutor
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.RunProcessFunction
import net.bramp.ffmpeg.builder.FFmpegBuilder
import java.io.File


class Converter {

  private var ffmpeg: FFmpeg
  private val ffprobe: FFprobe
  private var builder: FFmpegBuilder
  private var executor: FFmpegExecutor

  init {
    // Initialize ffmpeg wrapper according to ffmpeg-wrapper-cli-docs
    val func = RunProcessFunction()
    // We place the ffmpeg binary in the resource folder so that we can easily
    // use serveless for deployment
    val ffmpegPath = this.javaClass.classLoader.getResource("ffmpeg").file
    val ffprobePath = this.javaClass.classLoader.getResource("ffprobe").file

    // Initialize ffmpeg wrapper
    ffmpeg = FFmpeg(ffmpegPath, func)
    ffprobe = FFprobe(ffprobePath)
    builder = FFmpegBuilder()
    executor = FFmpegExecutor(ffmpeg)
  }

  /**
   * Converts the file to whatever format you wish
   *
   * @param input
   *            THe file path of the input file
   * @param output
   *            The directory and file name for the output
   *
   * @return A Boolean indicating success/failure
   *
   */
  fun process(input: String,output: String): Boolean {

    // Create the output directory
    File("/tmp/$output").mkdirs()

    // Setup the builder with all our conversion options
    builder.setInput(input)
      builder.addOutput("/tmp/$output/$output.mp4")
          .setAudioBitRate(320000)
          .done()
      builder.addOutput("/tmp/$output/$output-256k.mp4")
          .setAudioBitRate(256000)
          .done()

      builder.addOutput("/tmp/$output/$output-128k.mp4")
          .setAudioBitRate(128000)
          .done()
     builder.addOutput("/tmp/$output/$output-128k.mp4")
         .setAudioBitRate(64000)
         .done()
      
    // Execute the converter and return its status  
      return try {
        executor.createJob(builder).run()
        val msg = "[x] File converted successfully."
        println(msg)
        true
      } catch (ex: Exception) {
        println(ex)
        false
      }
    }

}

