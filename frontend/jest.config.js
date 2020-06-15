module.exports = {
  // roots: ['<rootDir>/src'],
  transform: {
    '^.+\\.tsx?$': 'ts-jest',
  },
  testMatch: [

    "**/*interceptor.service.spec.ts"
  ],
  testResultsProcessor: "jest-sonar-reporter"
  // testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.tsx?$',
  // moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
}
