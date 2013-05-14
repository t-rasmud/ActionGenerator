/**
 * Copyright 2010 Sematext International
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sematext.ag.metric;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Meter;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;

import java.util.concurrent.TimeUnit;

/**
 * Basic metrics class.
 * 
 * @author sematext, http://www.sematext.com/
 */
public class BasicMetrics {
  protected Meter sinkRequests;
  protected Timer sinkTimer, requestsTimer;
  private TimerContext sinkTimerContext;

  /**
   * Constructor.
   * 
   * @param clazz
   *          class for calculating metrics
   */
  public BasicMetrics(Class<?> clazz) {
    sinkRequests = Metrics.newMeter(clazz, "sinkRequests", "sinkRequests", TimeUnit.SECONDS);
    sinkTimer = Metrics.newTimer(clazz, "responses", TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
    requestsTimer = Metrics.newTimer(clazz, "requests", TimeUnit.MILLISECONDS, TimeUnit.SECONDS);
  }

  /**
   * Increment number of requests.
   */
  public void incrementRequests() {
    sinkRequests.mark();
  }

  /**
   * Starts sink timer.
   */
  public void startSinkTimer() {
    sinkTimerContext = sinkTimer.time();
  }

  /**
   * Starts request timer.
   * 
   * @return timer context
   */
  public TimerContext startTimer() {
    return requestsTimer.time();
  }

  /**
   * Stops sink timer.
   */
  public void stopSinkTimer() {
    sinkTimerContext.stop();
  }

  /**
   * Stops request timer.
   * 
   * @param requestTimerContext
   *          timer context
   */
  public void stopTimer(TimerContext requestTimerContext) {
    requestTimerContext.stop();
  }
}
