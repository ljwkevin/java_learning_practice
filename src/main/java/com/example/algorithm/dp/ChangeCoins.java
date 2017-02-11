package com.example.algorithm.dp;

/**
 * Created by lichao on 2017/2/4.
 */
public class ChangeCoins {
  public static void main(String[] args) {

  }

  /**
   * 方法一：暴力递归
   */
  public static int coins1(int[] arr, int aim) {
    if (arr == null || arr.length == 0 || aim < 0) {
      return 0;
    }
    return process1(arr, 0, aim);
  }

  public static int process1(int[] arr, int index, int aim) {
    int result = 0;
    if (index == arr.length) {
      result = aim == 0 ? 1 : 0;
    } else {
      for (int i = 0; arr[index] * i <= aim; i++) {
        result += process1(arr, index + 1, aim);
      }
    }

    return result;
  }

  /**
   * 方法二：记忆搜索法,保存下每一次递归的结果值，下次如果有就直接拿值，省去重复的计算
   * map[i][j]=-1表示递归过程计算过，但是result为0，map[i][j]=0表示没有计算过
   */
  public static int coins2(int[] arr, int aim) {
    if (arr == null || arr.length == 0 || aim < 0) {
      return 0;
    }
    int[][] map = new int[arr.length + 1][aim + 1];

    return process2(arr, 0, aim, map);
  }

  public static int process2(int[] arr, int index, int aim, int[][] map) {
    int result = 0;
    if (index == arr.length) {
      result = aim == 0 ? 1 : 0;
    } else {
      for (int i = 0; arr[index] * i <= aim; i++) {
        int mapValue = map[index + 1][aim - arr[index] * i];
        if (mapValue != 0) {
          result += mapValue == -1 ? 0 : mapValue;
        } else {
          result += process2(arr, index + 1, aim, map);
        }
      }
    }

    map[index][aim] = result == 0 ? -1 : result;
    return result;
  }

  /**
   * 方法三：动态规划
   */
  public static int coins3(int[] arr, int aim) {
    if (arr == null || arr.length == 0 || aim < 0) {
      return 0;
    }

    int[][] dp = new int[arr.length][aim + 1];

    for (int i = 0; i < arr.length; i++) {
      //组成金钱0的种数为1
      dp[i][0] = 1;
    }

    for (int j = 0; arr[0] * j < aim; j++) {
      dp[0][arr[0] * j] = 1;
    }

    int num;
    for (int i = 1; i < arr.length; i++) {
      for (int j = 1; j <= aim; j++) {
        num = 0;
        for (int k = 0; j - arr[j] * k >= 0; k++) {
          num += dp[i - 1][j - arr[j] * k];
        }
        dp[i][j] = num;
      }
    }

    return dp[arr.length - 1][aim];
  }
}