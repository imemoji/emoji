package com.imvc.emoji

import android.content.Context
import android.content.res.AssetManager
import java.io.IOException

class Utility {
    companion object {
        fun listAssetsFiles(context: Context,
                            path: String = "",
                            recursive:Boolean = false,
                            excludedFolders: List<String> = listOf("images", "webkit")
        ): List<String> {
            val assetManager: AssetManager = context.assets
            val fileList = mutableListOf<String>()

            try {
                // 列出指定路径下的所有内容（文件和文件夹）
                val files = assetManager.list(path) ?: return fileList
                for (fileName in files) {
                    val filePath = if (path.isEmpty()) fileName else "$path/$fileName"
                    // 如果当前文件夹在排除列表中，则跳过
                    if (excludedFolders.contains(fileName.lowercase())) {
                        continue
                    }
                    // 递归判断当前路径是否为文件夹
                    if (assetManager.list(filePath)?.isNotEmpty() == true && recursive) {
                        fileList.addAll(listAssetsFiles(context, filePath,recursive)) // 递归获取子目录
                    } else {
                        fileList.add(filePath) // 添加文件路径到列表中
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }

            return fileList
        }
    }


}