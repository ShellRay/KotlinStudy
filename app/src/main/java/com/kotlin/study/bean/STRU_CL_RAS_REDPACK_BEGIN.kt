package com.kotlin.study.bean


import java.io.IOException
import java.io.Serializable
import java.nio.ByteBuffer

/**
 *
 */

class STRU_CL_RAS_REDPACK_BEGIN @Throws(IOException::class)
constructor(data: ByteBuffer) : Serializable {

    val m_i64RID: Long            //红包ID
    val m_lRoomID: Int            //房间ID
    val m_byMicIndex: Byte        //麦序
    val m_i64SpeakerID: Long    //主播ID
    val m_iDurationTime: Int   //红包持续时间，单位(s)
    val m_iTotalPackage: Int   //总红包个数
    val m_iPackagePerScreen: Int//单屏红包个数
    val m_iCurrentFreeGiftCount: Int

    init {

        m_i64RID = data.long
        m_lRoomID = data.int
        m_byMicIndex = data.int.toByte()
        m_i64SpeakerID = data.long
        m_iDurationTime = data.int
        m_iTotalPackage = data.int
        m_iPackagePerScreen = data.int
        m_iCurrentFreeGiftCount = data.int
    }

}
