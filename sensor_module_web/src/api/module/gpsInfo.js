import Http from '@/api/http.js'

export const gpsInfo = function (data) {
  return Http.post(
    '/api/sensormodule/gpsInfo/getTransportRoute?transportInfoId=',
    data
  )
}
