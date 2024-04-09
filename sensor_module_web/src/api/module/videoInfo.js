import Http from '@/api/http.js'

export const info = function (transportObj) {
  return Http.post('/api/sensormodule/videoInfo/viewMonitor', transportObj)
}
