package com.yiyiaddon.e.i.f;

import java.util.Objects;

public record a(b a, String lN) {
   public a(b a, String lN) {
      Objects.requireNonNull(
         a,
         (String)com.yiyiaddon.m.b.a<"sz6hffauux4b9","yoiV2CEEMfEnQb/rbfgbhoVn+tFAwyKnSUDSHZBbH9m0smX2Hk9yBQ==",-4831191199446316750,2865973394142897019,-5078258853626062326,-8560523470962555368>()
      );
      lN = Objects.requireNonNullElse(
         lN,
         (String)com.yiyiaddon.m.b.a<"s107lnmk11nech","cyln72RsNeopC79nR/QI4F3FSexWbYHd0QXpFA==",7312293635796920947,7515796660870249787,-4922210650410772045,3340530340696391837>()
      );
      this.a = a;
      this.lN = lN;
   }

   public static a b() {
      return new a(
         b.SUCCESS,
         (String)com.yiyiaddon.m.b.a<"s107lnmk11nech","cyln72RsNeopC79nR/QI4F3FSexWbYHd0QXpFA==",7312293635796920947,7515796660870249787,-4922210650410772045,3340530340696391837>()
      );
   }

   public static a c() {
      return new a(
         b.WAITING,
         (String)com.yiyiaddon.m.b.a<"s107lnmk11nech","cyln72RsNeopC79nR/QI4F3FSexWbYHd0QXpFA==",7312293635796920947,7515796660870249787,-4922210650410772045,3340530340696391837>()
      );
   }

   public static a a(String var0) {
      return new a(b.RETRY, var0);
   }

   public static a b(String var0) {
      return new a(b.FAILED, var0);
   }

   public String br() {
      return this.lN;
   }
}
