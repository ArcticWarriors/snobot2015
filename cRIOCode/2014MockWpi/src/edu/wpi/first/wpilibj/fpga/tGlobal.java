// Copyright (c) National Instruments 2009.  All Rights Reserved.
// Do Not Edit... this file is generated!

package edu.wpi.first.wpilibj.fpga;

public class tGlobal extends tSystem
{

   public tGlobal()
   {
      super();

   }

   protected void finalize()
   {
      super.finalize();
   }

   public static final int kNumSystems = 1;








//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Version
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kGlobal_Version_Address = 0x8118;

   public static int readVersion()
   {
       return 0;
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for LocalTime
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kGlobal_LocalTime_Address = 0x8110;

   public static long readLocalTime()
   {
       return 0;
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for FPGA_LED
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kGlobal_FPGA_LED_Address = 0x810C;

   public static void writeFPGA_LED(final boolean value)
   {
   }
   public static boolean readFPGA_LED()
   {
       return false;
   }

//////////////////////////////////////////////////////////////////////////////////////////////////
// Accessors for Revision
//////////////////////////////////////////////////////////////////////////////////////////////////
   private static final int kGlobal_Revision_Address = 0x8114;

   public static long readRevision()
   {
       return 0;
   }




}
