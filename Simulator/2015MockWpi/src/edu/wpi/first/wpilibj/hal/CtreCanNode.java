/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.11
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package edu.wpi.first.wpilibj.hal;


public class CtreCanNode {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected CtreCanNode(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CtreCanNode obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        CanTalonJNI.delete_CtreCanNode(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public CtreCanNode(SWIGTYPE_p_UINT8 deviceNumber) {
    this(CanTalonJNI.new_CtreCanNode(SWIGTYPE_p_UINT8.getCPtr(deviceNumber)), true);
  }

  public SWIGTYPE_p_UINT8 GetDeviceNumber() {
    return new SWIGTYPE_p_UINT8(CanTalonJNI.CtreCanNode_GetDeviceNumber(swigCPtr, this), true);
  }

}
