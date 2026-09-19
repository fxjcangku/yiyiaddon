package com.yiyiaddon.d.a;

import net.minecraft.network.chat.Component;

public record h(String R, Component a, String S) {
   public static final String T = "action_bar";
   public static final String U = "title";
   public static final String V = "subtitle";
   public static final String W = "chat";
   public static final String X = "chat_overlay";
   public static final String Y = "tab_list_header";
   public static final String Z = "tab_list_footer";
   public static final String aa = "objective_title";
   public static final String ab = "objective_removed";
   public static final String ac = "score_display";
   public static final String ad = "score_owner";
   public static final String ae = "team_display";
   public static final String af = "team_prefix";
   public static final String ag = "team_suffix";
   public static final String ah = "boss_bar";

   public String o() {
      return this.R;
   }

   public String r() {
      return this.S;
   }
}
